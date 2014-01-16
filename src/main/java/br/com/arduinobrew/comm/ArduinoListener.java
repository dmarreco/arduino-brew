package br.com.arduinobrew.comm;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.exception.ArduinoComunicationException;
import br.com.arduinobrew.repo.TicketParser;

@ApplicationScoped
public class ArduinoListener implements SerialPortEventListener
{
  @Inject
  private Event<Ticket>       newTicketReceivedEvent;

  @Inject
  private TicketParser        ticketParser;
  
  @Inject
  private Logger              log;

  /**
   * The port we're normally going to use.
   **/
  private SerialPort          serialPort;

  private static final String PORT_NAMES[] = {
      "/dev/tty.usbserial-A9007UX1", // Mac OS X
      "/dev/ttyUSB0", // Linux
      "COM3", // Windows
  };

  /**
   * A BufferedReader which will be fed by a InputStreamReader converting the bytes into characters making the displayed
   * results codepage independent
   */
  private BufferedReader      input;

  /** The output stream to the port */
  private OutputStream        output;

  /** Milliseconds to block while waiting for port open */
  private static final int    TIME_OUT     = 2000;

  /** Default bits per second for COM port. */
  private static final int    DATA_RATE    = 9600;

  /**
   * @throws ArduinoComunicationException Se não conseguiu encontrar uma porta
   */
  @PostConstruct
  public void initialize()
  {
    log.info("Detectando porta serial");
    
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    // First, Find an instance of serial port as set in PORT_NAMES.
    topLoop: while (portEnum.hasMoreElements())    {
      CommPortIdentifier currPortId = (CommPortIdentifier)portEnum.nextElement();
      for (String portName : PORT_NAMES)      {
        if (currPortId.getName().equals(portName))        {
          portId = currPortId;
          break topLoop;
        }
      }
    }
    if (portId == null)    {
      log.error("Não foi possível encontrar uma porta serial");
    }

    try    {
      log.info("Estabelecendo conexão com porta serial [" + portId + "]");
      
      // open serial port, and use class name for the appName.
      serialPort = (SerialPort)portId.open(this.getClass().getName(), TIME_OUT);

      // set port parameters
      serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

      // open the streams
      input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
      output = serialPort.getOutputStream();

      // add event listeners
      serialPort.addEventListener(this);
      serialPort.notifyOnDataAvailable(true);
    }
    catch (Exception e)    {
      log.error("Erro estabelecendo conexão serial", e);
    }
  }

  /**
   * This should be called when you stop using the port. This will prevent port locking on platforms like Linux.
   */
  @PreDestroy
  public synchronized void close()    {
    log.info("Fechando conexão com porta serial");
    
    if (serialPort != null)    {
      serialPort.removeEventListener();
      serialPort.close();
    }
  }

  /**
   * Handle an event on the serial port. Read the data and print it.
   */
  @Override
  public synchronized void serialEvent(SerialPortEvent oEvent)
  {
    log.info("Evento recebido da porta serial: [" + oEvent.getEventType() + "]");
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)    {
      try {
        String ticketAsString = input.readLine();
        Ticket ticket = ticketParser.deserialize(ticketAsString);
        newTicketReceivedEvent.fire(ticket);
      }
      catch (Exception e) {
        log.error("Erro processando ticket", e);
      }
    }
  }

}

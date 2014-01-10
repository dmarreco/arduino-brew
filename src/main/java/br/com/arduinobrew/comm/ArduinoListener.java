package br.com.arduinobrew.comm;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.com.arduinobrew.domain.Ticket;

public class ArduinoListener  implements SerialPortEventListener 
{
  @Inject
  private Event<Ticket> newTicketReceivedEvent;


  @Override
  public void serialEvent(SerialPortEvent serialPortEvent)
  {
    // TODO Auto-generated method stub
    
    /*
     * ...
     * Ticket ticket = parse(ticketAsString);
     * newTicketReceivedEvent.fire(newTicket);
     */
  }

}

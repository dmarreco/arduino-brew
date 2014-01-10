package br.com.arduinobrew.exception;

/**
 * Lançado no caso de falha na comunicação serial com o Arduino.
 * 
 * @author dmarreco
 */
public class ArduinoComunicationException extends Exception
{
  private static final long serialVersionUID = 7976006380173532785L;

  public ArduinoComunicationException (String msg) {
    super (msg);
  }
  
  public ArduinoComunicationException (String msg, Throwable cause) {
    super(msg,cause);
  }

}

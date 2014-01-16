package br.com.arduinobrew.exception;

/**
 * Lançada quando há erro na persistência
 * 
 * @author dmarreco
 *
 */
public class ArduinoPersistenceException extends RuntimeException
{
  private static final long serialVersionUID = -5211158030736962238L;

  public ArduinoPersistenceException (String msg) {
    super (msg);
  }
  
  public ArduinoPersistenceException (String msg, Throwable cause) {
    super(msg, cause);
  }

}

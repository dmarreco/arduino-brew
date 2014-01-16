package br.com.arduinobrew.exception;

/**
 * Lançada ao pelo parser caso a string recebida não corresponda a um ticket válido
 * 
 * @author dmarreco
 */
public class TicketParsingException extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public TicketParsingException (String message) {
    super(message);
  }

}

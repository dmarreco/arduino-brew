package br.com.arduinobrew.repo;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.arduinobrew.domain.Fase;
import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.exception.TicketParsingException;

/**
 * Deserializes ticket string (as received from serial) to it's object format. Expected input format:<br/>
 * elapsedTime;fase;temperaturaBrassagem;temperaturaFervura;texto
 * 
 * @author dmarreco
 */
public class TicketParser
{
  private Pattern ticketRegexPattern = Pattern.compile("(\\d*);([A-Z]?);(.*);(.*);(.*)");
  
  public Ticket deserialize(String ticketAsString) throws TicketParsingException
  {
    validateTicket(ticketAsString);
    
    Ticket ticket = new Ticket();
    ticket.setTicketAsString(ticketAsString);

    String[] tokens = ticketAsString.split(";", 5);

    ticket.setElapsedTimeMilis("".equals(tokens[0]) ? null : Long.parseLong(tokens[0]));
    ticket.setFase(Fase.getByCod(tokens[1]));
    ticket.setTemperaturaBrassagem("".equals(tokens[2]) ? null : new BigDecimal(tokens[2]));
    ticket.setTemperaturaFervura("".equals(tokens[3]) ? null : new BigDecimal(tokens[3]));
    ticket.setTexto("".equals(tokens[4]) ? null : tokens[4]);

    return ticket;
  }
  
  /**
   * Valida se um ticket está em um formato válido
   * 
   * @param ticketAsString
   */
  private void validateTicket(String ticketAsString) throws TicketParsingException
  {
    Matcher m = ticketRegexPattern.matcher(ticketAsString);
    if (!m.matches())
      throw new TicketParsingException ("Ticket no formato inválido: [" + ticketAsString + "]");
  }

}

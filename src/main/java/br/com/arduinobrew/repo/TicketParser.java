package br.com.arduinobrew.repo;

import java.math.BigDecimal;

import br.com.arduinobrew.domain.Fase;
import br.com.arduinobrew.domain.Ticket;

/**
 * Deserializes ticket string (as received from serial) to it's object format. Expected input format:<br/>
 * elapsedTime;fase;temperaturaBrassagem;temperaturaFervura;texto
 * 
 * @author dmarreco
 */
public class TicketParser
{
  public Ticket deserialize(String ticketAsString)
  {
    Ticket ticket = new Ticket();
    ticket.setTicketAsString(ticketAsString);

    String[] tokens = ticketAsString.split(";", 6);

    ticket.setElapsedTimeMilis("".equals(tokens[0]) ? null : Long.parseLong(tokens[0]));
    ticket.setFase(Fase.getByCod(tokens[1]));
    ticket.setTemperaturaBrassagem("".equals(tokens[2]) ? null : new BigDecimal(tokens[2]));
    ticket.setTemperaturaFervura("".equals(tokens[3]) ? null : new BigDecimal(tokens[3]));
    ticket.setTexto("".equals(tokens[4]) ? null : tokens[4]);

    return ticket;
  }

}

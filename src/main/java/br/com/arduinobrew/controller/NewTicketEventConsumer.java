package br.com.arduinobrew.controller;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.com.arduinobrew.comm.ArduinoListener;
import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.repo.TicketDAO;

/**
 * Persiste um ticket recebido via serial por ArduinoListener
 * 
 * @see ArduinoListener
 * @author dmarreco
 */
public class NewTicketEventConsumer
{

  @Inject
  TicketDAO ticketRepo;

  public void persisteNovoTicket(@Observes
  Ticket newTicket)
  {
    try
    {
      ticketRepo.write(newTicket.getTicketAsString());
    }
    catch (Exception e)
    {
      // TODO log
      e.printStackTrace();
    }
  }

}

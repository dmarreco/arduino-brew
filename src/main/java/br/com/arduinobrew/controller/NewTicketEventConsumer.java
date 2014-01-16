package br.com.arduinobrew.controller;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

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
  private Logger log;

  @Inject
  private TicketDAO ticketRepo;

  public void persisteNovoTicket(@Observes Ticket newTicket)  {
    log.info("Recebido ticket: [{}]", newTicket.toString());
    try    {
      ticketRepo.write(newTicket.getTicketAsString());
    }
    catch (Exception e)    {
      log.error("Erro ao persistir ticket recebido: [{}]", newTicket.toString(),  e);
    }
  }

}

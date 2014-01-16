package br.com.arduinobrew.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.repo.TicketDAO;

/**
 * Gera os dados a serem exibidos no formato JSON
 * 
 * @author dmarreco
 */
@Path("/brew")
@RequestScoped
public class BrewDataRESTService
{

  @Inject
  private TicketDAO ticketDao;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Ticket> listAllTickets() {
      return ticketDao.getAll();
  }
  
}

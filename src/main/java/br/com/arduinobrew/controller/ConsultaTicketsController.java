package br.com.arduinobrew.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.exception.TicketParsingException;
import br.com.arduinobrew.repo.TicketDAO;


@Model
@RequestScoped
public class ConsultaTicketsController
{
  private List<Ticket> tickets;
  
  @Inject
  private TicketDAO ticketDAO;
  
  @Inject
  private Logger log;
  
  @Inject
  private FacesContext facesContext;

  @PostConstruct
  public void init() throws TicketParsingException  {
    log.info("Recebido request para listagem de tickets");
    this.tickets = ticketDAO.getAll();
  }

  @Produces
  @Named
  public List<Ticket> getTickets()  {
    return this.tickets;
  }

}

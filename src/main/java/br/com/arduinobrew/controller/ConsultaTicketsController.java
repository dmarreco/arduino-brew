package br.com.arduinobrew.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.exception.TicketParsingException;
import br.com.arduinobrew.repo.TicketParser;


@Model
@RequestScoped
public class ConsultaTicketsController
{
  private List<Ticket> tickets;
  
  @Inject
  private FacesContext facesContext;

  @PostConstruct
  public void init() throws TicketParsingException  {
    this.tickets = new ArrayList<Ticket>();
    
    TicketParser ticketParser = new TicketParser();
    tickets.add(ticketParser.deserialize("6530000;L;60.15;90.10;"));
    tickets.add(ticketParser.deserialize("6543210;L;62.15;100.03;"));
    tickets.add(ticketParser.deserialize("6545000;L;63.45;101.12;Processo interrompido: chama piloto [0] apagada"));
  }

  @Produces
  @Named
  public List<Ticket> getTickets()  {
    return this.tickets;
  }

}

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
@Path("/ticket")
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
  
  @GET  
  @Path("/hello")  
  @Produces("text/plain")  
  public String hello(){  
      return "Hello World!!! dineshonjava";      
  }
  
  @GET
  @Path("/testdata")  
  @Produces("text/plain")  
  public String testData(){  
      String str = 
          "# ----------------------------------------\n" +
          "# highcharts.com\n"+
          "# Audience Overview\n"+
          "# 20130309-20130408\n"+
          "# ----------------------------------------\n"+
          "Day,Visits,Unique Visitors\n"+
          "3/9/13,5691,4346\n"+
          "3/10/13,5403,4112\n"+
          "3/11/13,15574,11356\n"+
          "3/12/13,16211,11876\n"+
          "3/13/13,16427,11966\n"+
          "3/14/13,16486,12086\n"+
          "3/15/13,14737,10916\n"+
          "3/16/13,5838,4507\n"+
          "3/17/13,5542,4202\n";
      return str;
  }
  

}

package br.com.arduinobrew.repo;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.arduinobrew.domain.Fase;
import br.com.arduinobrew.domain.Ticket;

/*
 * Expected format:
 * elapsedTime;fase;temperaturaBrassagem;temperaturaFervura;texto;
 */
public class TicketParserTest
{
  
  private TicketParser ticketParser; 
  
  @Before
  public void before ()
  {
    this.ticketParser = new TicketParser();
  }
  
  @Test
  public void deserializa_elapsedTime_com_sucesso ()
  {
    String ticketAsString = "123456;;;;;";
    
    Ticket ticket = ticketParser.deserialize(ticketAsString);
    
    assertEquals(new Long(123456L), ticket.getElapsedTimeMilis());
    assertNull(ticket.getFase());
    assertNull(ticket.getTemperaturaBrassagem());
    assertNull(ticket.getTemperaturaFervura());
    assertNull(ticket.getTexto());
    assertEquals(ticketAsString, ticket.getTicketAsString());
  }
  
  @Test
  public void deserializa_texto_com_sucesso () 
  {
    String ticketAsString = ";;;;texto teste;";
    
    Ticket ticket = ticketParser.deserialize(ticketAsString);
    
    assertNull(ticket.getElapsedTimeMilis());
    assertNull(ticket.getFase());
    assertNull(ticket.getTemperaturaBrassagem());
    assertNull(ticket.getTemperaturaFervura());
    assertEquals("texto teste", ticket.getTexto());
    assertEquals(ticketAsString, ticket.getTicketAsString());
  }
  
  @Test
  public void deserializa_fase_com_sucesso () 
  {
    String ticketAsString = ";L;;;;";
    
    Ticket ticket = ticketParser.deserialize(ticketAsString);
    
    assertNull(ticket.getElapsedTimeMilis());
    assertEquals(ticket.getFase(), Fase.getByCod("L"));
    assertNull(ticket.getTemperaturaBrassagem());
    assertNull(ticket.getTemperaturaFervura());
    assertNull(ticket.getTexto());
    assertEquals(ticketAsString, ticket.getTicketAsString());
  }
  
  @Test
  public void deserializa_ticket_completo_com_sucesso ()
  {
    String ticketAsString = "6543210;L;62.15;100.03;Processo interrompido: chama piloto [0] apagada;";
    
    Ticket ticket = ticketParser.deserialize(ticketAsString);
    
    assertEquals(new Long(6543210L), ticket.getElapsedTimeMilis());
    assertEquals(Fase.LAVAGEM, ticket.getFase());
    assertEquals(new BigDecimal("62.15"), ticket.getTemperaturaBrassagem());
    assertEquals(new BigDecimal("100.03"), ticket.getTemperaturaFervura());
    assertEquals("Processo interrompido: chama piloto [0] apagada", ticket.getTexto());
  }


}

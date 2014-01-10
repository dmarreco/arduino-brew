package br.com.arduinobrew.controller;

import javax.inject.Inject;

import br.com.arduinobrew.repo.TicketDAO;

/**
 * Gera os dados a serem exibidos no formato JSON
 * 
 * @author dmarreco
 */
public class BrewDataRESTService
{
  
  @Inject 
  private TicketDAO ticketDao;

}

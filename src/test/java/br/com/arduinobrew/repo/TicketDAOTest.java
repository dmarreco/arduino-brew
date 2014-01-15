package br.com.arduinobrew.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arduinobrew.cfg.AppConfig;
import br.com.arduinobrew.cfg.CfgProp;
import br.com.arduinobrew.domain.Ticket;

public class TicketDAOTest
{
  @Mock
  private AppConfig appConfig;
  
  @Mock
  private TicketParser ticketParser;

  @InjectMocks
  private TicketDAO ticketRepo = new TicketDAO();
  
  @Before
  public void before () throws IOException {
     MockitoAnnotations.initMocks(this);
    
    when(appConfig.getProperty(CfgProp.DATA_DIR)).thenReturn("/var/lib/arduino-brew/test/");

    ticketRepo.init(); // chamado no @PostConstruct
  }
  
  @After
  public void after () throws IOException {
    ticketRepo.close(); // chamado no @PreDestroy
    
    // apaga o arquivo criado para persistencia
    if (!ticketRepo.getDataFile().delete()) {
      throw new IOException("Erro ao apagar dataFile: " + ticketRepo.getDataFile().getAbsolutePath());
    }
  }
  
  @Test
  public void getAll_em_dao_novo_retorna_lista_vazia () throws IOException {
    assertTrue(ticketRepo.getAll().isEmpty());
  }
  
  @Test
  public void put_de_um_registro_verifica_se_linha_foi_gravada_no_arquivo () throws IOException 
  {
    String ticketAsString = "6543210;L;62.15;100.03;Processo interrompido: chama piloto [0] apagada;";
    
    ticketRepo.write(ticketAsString);
    
    // verifica se linha foi gravada no arquivo
    BufferedReader r = new BufferedReader(new FileReader(ticketRepo.getDataFile()));
    String linhaGravada = r.readLine();
    assertEquals(ticketAsString, linhaGravada);
    assertNull(r.readLine()); // verifica se foi a única linha gravada
    r.close();
  }
  
  @Test
  public void put_de_um_registro_seguido_de_getAll_retorna_lista_com_um_ticket () throws IOException
  {
    String ticketAsString = "6543210;L;62.15;100.03;Processo interrompido: chama piloto [0] apagada;";

    Ticket ticketDeserializado = new Ticket();
    when(ticketParser.deserialize(ticketAsString)).thenReturn(ticketDeserializado);
    
    ticketRepo.write(ticketAsString);
    
    List<Ticket> ticketsGravados = ticketRepo.getAll();
    
    assertEquals(1, ticketsGravados.size());
    assertSame(ticketDeserializado, ticketsGravados.get(0));
  }
  
  @Test
  public void put_de_dois_registros_seguido_de_getAll_retorna_lista_com_dois_tickets () throws IOException
  {
    String ticketAsString1 = "6543210;L;62.15;100.03;Processo interrompido: chama piloto [0] apagada;";
    String ticketAsString2 = "ticket 2";

    Ticket ticketDeserializado1 = new Ticket();
    Ticket ticketDeserializado2 = new Ticket();
    when(ticketParser.deserialize(ticketAsString1)).thenReturn(ticketDeserializado1);
    when(ticketParser.deserialize(ticketAsString2)).thenReturn(ticketDeserializado2);
    
    ticketRepo.write(ticketAsString1);
    ticketRepo.write(ticketAsString2);
    
    List<Ticket> ticketsGravados = ticketRepo.getAll();
    
    assertEquals(2, ticketsGravados.size());
    assertSame(ticketDeserializado1, ticketsGravados.get(0));
    assertSame(ticketDeserializado2, ticketsGravados.get(1));
  }

}

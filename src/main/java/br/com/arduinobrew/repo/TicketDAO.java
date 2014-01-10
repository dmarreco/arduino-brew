package br.com.arduinobrew.repo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.arduinobrew.cfg.AppConfig;
import br.com.arduinobrew.cfg.CfgProp;
import br.com.arduinobrew.domain.Ticket;

/**
 * Provê acesso ao banco interno de tickets recebido do arduino
 * 
 * @author dmarreco
 *
 */
public class TicketDAO
{
  @Inject
  private TicketParser ticketParser;
  
  @Inject 
  private AppConfig appConfig;
  
  private File dataFile;
  private BufferedWriter writer;
  
  /**
   * Cria um novo arquivo para persistencia dos bilhetes recebidos e abre um writer para escrita 
   */
  @PostConstruct
  public void init () throws IOException
  {
    DateFormat dataFileNameDateFormat = new SimpleDateFormat ("yyyyMMddHHmmssS");
    String dataFileName = "brew_" + dataFileNameDateFormat.format(new Date());
    
    String repoDirPath = appConfig.getProperty(CfgProp.DATA_DIR);
    
    File repoDirectory = new File (repoDirPath);
    if (!repoDirectory.exists()) {
      repoDirectory.mkdirs();
    }
   
    dataFile = new File (repoDirectory, dataFileName);
    
    dataFile.createNewFile();
    
    writer = new BufferedWriter(new FileWriter (dataFile));
  }
  
  /**
   * Fecha o arquivo para escrita 
   */
  @PreDestroy
  public void close() throws IOException
  {
    writer.close();
  }
  
  
  /**
   * Retorna todos os tickets emitidos pelo Arduino e armazenados na base local (em arquivo) 
   * desde o início do processo de brassagem
   */
  public List<Ticket> getAll () throws IOException
  {
    BufferedReader reader = null;
    List<Ticket> res = new ArrayList<Ticket>();
    
    synchronized (writer) { //espera outra thread terminar de escrever antes de fazer a leitura    
      try {
        reader = new BufferedReader (new FileReader (dataFile));
        String ticketAsString;
        while ( (ticketAsString = reader.readLine()) != null) {
          Ticket ticket = ticketParser.deserialize(ticketAsString);
          res.add(ticket);
        }
      }
      finally {
        if (reader != null) {
          reader.close();
        }
      }
    }
    
    return res;
  }
  
  /**
   * Armazena um novo ticket na base local em arquivo (previamente aberto para escrita) 
   */
  public void put (String ticketAsString) throws IOException 
  {
    synchronized (writer) {
      writer.write(ticketAsString);
      writer.newLine();
      writer.flush();
    }
  }
  
  public File getDataFile () {
    return this.dataFile;
  }

}

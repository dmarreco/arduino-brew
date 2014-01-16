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

import org.slf4j.Logger;

import br.com.arduinobrew.cfg.AppConfig;
import br.com.arduinobrew.cfg.CfgProp;
import br.com.arduinobrew.domain.Ticket;
import br.com.arduinobrew.exception.ArduinoPersistenceException;
import br.com.arduinobrew.exception.TicketParsingException;

/**
 * Provê acesso ao banco interno de tickets recebido do arduino.
 * 
 * Inicializa um novo arquivo (e um novo processo de brassagem) cada vez que
 * a aplicação inicia.
 * 
 * @author dmarreco
 */
@ApplicationScoped
public class TicketDAO
{
  @Inject
  private TicketParser   ticketParser;
  
  @Inject 
  private Logger         log;

  @Inject
  private AppConfig      appConfig;

  /**
   * O arquivo que contém os dados
   */
  private File           dataFile;
  
  /**
   * Um writer para o arquivo de dados
   */
  private BufferedWriter writer;
  
  private static final String HEADER = "Tempo;Fase;Temperatura na Brassagem;Temperatura na Fervura;Mensagem Texto";

  /**
   * Cria um novo arquivo para persistencia dos bilhetes recebidos e abre um writer para escrita
   */
  @PostConstruct
  public void init()
  {
    try {
      DateFormat dataFileNameDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_S");
      String dataFileName = "" + dataFileNameDateFormat.format(new Date()) + ".brew";
      
      String repoDirPath = appConfig.getProperty(CfgProp.DATA_DIR);
  
      File repoDirectory = new File(repoDirPath);
      if (!repoDirectory.exists())    {
        repoDirectory.mkdirs();
      }

      dataFile = new File(repoDirectory, dataFileName);

      log.info("Inicializando arquivo de tickets: {}", dataFile.getAbsoluteFile());
      
      dataFile.createNewFile();
  
      writer = new BufferedWriter(new FileWriter(dataFile));
  
      // Escreve a linha de cabeçalho
      writer.write(HEADER);
      writer.newLine();
      writer.flush();
    }
    catch (Exception e) {
      throw new ArduinoPersistenceException ("Erro ao tentar inicializar arquivo", e);
    }
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
   * Retorna todos os tickets emitidos pelo Arduino e armazenados na base local (em arquivo) desde o início do processo
   * de brassagem
   */
  public List<Ticket> getAll()
  {
    BufferedReader reader = null;
    List<Ticket> res = new ArrayList<Ticket>();

    synchronized (writer)    { // espera outra thread terminar de escrever antes de fazer a leitura
      try      {
        reader = new BufferedReader(new FileReader(dataFile));
        String ticketAsString;
        reader.readLine(); // descarta a primeira linha (cabeçalho)
        while ((ticketAsString = reader.readLine()) != null)        {
          Ticket ticket;
          try {
            ticket = ticketParser.deserialize(ticketAsString);
            res.add(ticket);
          }
          catch (TicketParsingException tpe) {
            log.error("Ticket fora do formato esperado: [" + ticketAsString + "]", tpe);
          }
        }
      }
      catch (IOException e) {
        throw new ArduinoPersistenceException ("Erro ao ler de arquivo", e);
      }
      finally      {
        try {
          if (reader != null)
            reader.close();
        }
        catch (IOException e) {
          throw new ArduinoPersistenceException("Erro ao tentar fechar arquivo", e);
        }
      }
    }

    return res;
  }

  /**
   * Armazena um novo ticket na base local em arquivo (previamente aberto para escrita)
   */
  public void write(String ticketAsString)
  {
    try {
      synchronized (writer)    {
        writer.write(ticketAsString);
        writer.newLine();
        writer.flush();
      }
    }
    catch (IOException e) {
      throw new ArduinoPersistenceException("Erro ao escrever ticket em arquivo: [" + ticketAsString + "]", e);
    }
  }
  
  public File getDataFile()  {
    return this.dataFile;
  }

}

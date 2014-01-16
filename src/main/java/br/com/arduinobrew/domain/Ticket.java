package br.com.arduinobrew.domain;

import java.math.BigDecimal;

/**
 * Representa um 'ticket' recebido do arduino via serial. Contém todas as informações relevantes sobre o processo de
 * brewing: medições dos sensores, status dos controles, etc.
 * 
 * @author dmarreco
 */
public class Ticket
{
  /**
   * ticket no formato enviado pelo arduino (CSV String)
   */
  private String     ticketAsString;

  /**
   * Tempo (em milisegundos) transcorrido desde o início do processo de brassagem
   */
  private Long       elapsedTimeMilis;

  /**
   * Fase corrente da brassagem
   */
  private Fase       fase;

  /**
   * Temperatura da panela de brassagem (em graus celsius)
   */
  private BigDecimal temperaturaBrassagem;

  /**
   * Temperatura da panela de fervura e de água da filtragem (em graus celsius)
   */
  private BigDecimal temperaturaFervura;

  /**
   * Mensagem texto (erro ou informação relevante)
   */
  private String     texto;

  public Long getElapsedTimeMilis()  {
    return elapsedTimeMilis;
  }

  public void setElapsedTimeMilis(Long elapsedTimeMilis)  {
    this.elapsedTimeMilis = elapsedTimeMilis;
  }

  public Fase getFase()  {
    return fase;
  }

  public void setFase(Fase fase)  {
    this.fase = fase;
  }

  public BigDecimal getTemperaturaBrassagem()  {
    return temperaturaBrassagem;
  }

  public void setTemperaturaBrassagem(BigDecimal temperaturaBrassagem)  {
    this.temperaturaBrassagem = temperaturaBrassagem;
  }

  public BigDecimal getTemperaturaFervura()  {
    return temperaturaFervura;
  }

  public void setTemperaturaFervura(BigDecimal temperaturaFervura)  {
    this.temperaturaFervura = temperaturaFervura;
  }

  public String getTexto()  {
    return texto;
  }

  public void setTexto(String texto)  {
    this.texto = texto;
  }

  public String getTicketAsString()  {
    return this.ticketAsString;
  }

  public void setTicketAsString(String ticketAsString)  {
    this.ticketAsString = ticketAsString;
  }
  
  @Override
  public String toString () {
    return getTicketAsString();
  }

}

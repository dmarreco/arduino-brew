package br.com.arduinobrew.domain;

/**
 * Fases do processo de brassagem
 * 
 * @author dmarreco
 *
 */
public enum Fase
{
  BRASSAGEM("B"),
  MASHOUT("M"),
  RECIRCULACAO("C"),
  LAVAGEM("L"),
  FERVURA("F"),
  RESFRIAMENTO("R");
  
  /**
   * Código (sigla) que representa a fase, conforme vem no ticket 
   * 
   * @see Ticket
   */
  private String cod;

  private Fase (String cod) {
    this.cod = cod;
  }
  
  public String getCod () {
    return this.cod;
  }
  
  /**
   * retorna a fase identificada pelo código fornecido.
   * 
   * @return a fase identificada pelo código fornecido. null caso o código não identifique nenhuma fase;
   */
  public static Fase getByCod (String cod) {
    for (Fase fase : Fase.values()) {
      if (fase.getCod().equals(cod))
        return fase;
    }
    return null;
  }
}

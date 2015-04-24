package br.com.arduinobrew.domain;

/**
 * Fases do processo de brassagem
 * 
 * @author dmarreco
 */
public enum Phase
{
  BRASSAGEM("B"), MASHOUT("M"), RECIRCULACAO("C"), LAVAGEM("L"), FERVURA("F"), RESFRIAMENTO("R");

  /**
   * Código (sigla) que representa a fase, conforme vem no ticket
   * 
   * @see Ticket
   */
  private String cod;

  private Phase(String cod)
  {
    this.cod = cod;
  }

  public String getCod()
  {
    return this.cod;
  }

  /**
   * retorna a fase identificada pelo código fornecido.
   * 
   * @return a fase identificada pelo código fornecido. null caso o código não identifique nenhuma fase;
   */
  public static Phase getByCod(String cod)
  {
    for (Phase fase : Phase.values())
    {
      if (fase.getCod().equals(cod))
        return fase;
    }
    return null;
  }
}

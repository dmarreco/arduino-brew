package br.com.arduinobrew.cfg;

import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * Usado para acessar as configurações de sistemas dependentes de ambiente, declaradas em 
 * arquivo .properties 
 * 
 * @author dmarreco
 */
public class AppConfig
{
  private Properties properties;
  
//  public AppConfig () {
//    load();
//  }
  
  public void load ()
  {
    try {
      //properties.load(AppConfig.class.getClassLoader().getResourceAsStream("config.properties"));
    }
    catch (Exception e) {
      //log.error("Erro ao carregar configuração", e);
    }
  }

  public String getProperty (CfgProp key)
  {
    return properties.getProperty(key.toString());
  }
}

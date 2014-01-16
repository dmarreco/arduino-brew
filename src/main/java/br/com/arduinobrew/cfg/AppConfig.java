package br.com.arduinobrew.cfg;

import java.io.IOException;
import java.util.Properties;

/**
 * Usado para acessar as configurações de sistemas dependentes de ambiente, declaradas em arquivo .properties
 * 
 * @author dmarreco
 */
public class AppConfig
{
  private Properties properties;

   public AppConfig () throws IOException {
     load();
   }

  public void load() throws IOException  {
    properties.load(AppConfig.class.getClassLoader().getResourceAsStream("config.properties"));
  }

  public String getProperty(CfgProp key)  {
    return properties.getProperty(key.toString());
  }
  
}

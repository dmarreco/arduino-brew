package br.com.arduinobrew.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 */
public class WebResources
{

  @Produces
  @RequestScoped
  public FacesContext produceFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }

  @Produces
  public Logger produceLog(InjectionPoint injectionPoint)
  {
    return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
  }

}

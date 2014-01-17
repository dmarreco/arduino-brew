package br.com.arduinobrew.util;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link javax.ws.rs.core.Application} and annotated with @ApplicationPath is the Java EE 6 "no XML"
 * approach to activating JAX-RS.
 * <p>
 * Resources are served relative to the servlet path specified in the {@link javax.ws.rs.ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rest/")
public class ArduinoBrewJaxRsActivator extends Application
{

}

//package br.com.arduinobrew.repo.integrated;
//
//import javax.inject.Inject;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import br.com.arduinobrew.comm.ArduinoListener;
//
//@RunWith(Arquillian.class)
//public class IntegrationTests
//{
//  @Deployment
//  public static JavaArchive createTestArchive() {
//      return ShrinkWrap.create(JavaArchive.class, "arduino-brew-web.war")
//              .addPackages(true,
//                      "br.com.arduinobrew")
//              .addAsResource("config.properties", "config.properties")
//              .addAsResource("logback-test.xml", "logback.xml")
//              .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//              .addAsManifestResource("cdv-mobili-test-ds.xml", "cdv-mobili-test-ds.xml");
//  }
//  
//  @Inject
//  private ArduinoListener arduinoListener;
//  
//  @Test
//  public void caminho_feliz_recebe_ticket_valido_ticket_aparece_na_consulta ()
//  {
////    como mandar uma mensgagem para a porta serial?
//    
//  }
//
//}

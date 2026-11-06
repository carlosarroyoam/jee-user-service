package com.carlosarroyoam.user.service.producer;

import java.util.logging.Logger;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

/** Productor CDI de {@link Logger} nombrados según la clase que los inyecta. */
public class LoggerProducer {
  /**
   * Crea un logger cuyo nombre es el de la clase del bean donde se inyecta.
   *
   * @param injectionPoint el punto de inyección que solicita el logger
   * @return el {@link Logger} de la clase que lo inyecta
   */
  @Produces
  @Dependent
  public Logger createLogger(InjectionPoint injectionPoint) {
    return Logger.getLogger(injectionPoint.getBean().getBeanClass().getName());
  }
}

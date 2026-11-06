package com.carlosarroyoam.user.service.producer;

import java.time.Clock;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

/** Productor CDI del {@link Clock} de la aplicación. */
public class ClockProducer {
  /**
   * Reloj del sistema con la zona horaria por defecto de la JVM.
   *
   * @return el {@link Clock} de la aplicación
   */
  @Produces
  @Dependent
  public Clock createClock() {
    return Clock.systemDefaultZone();
  }
}

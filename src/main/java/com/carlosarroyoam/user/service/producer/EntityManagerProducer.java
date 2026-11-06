package com.carlosarroyoam.user.service.producer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Productor CDI del {@link EntityManager}: mantiene un único {@link EntityManagerFactory} y expone
 * un {@code EntityManager} por petición.
 */
@ApplicationScoped
public class EntityManagerProducer {
  private EntityManagerFactory entityManagerFactory;

  /** Crea el {@link EntityManagerFactory} de la unidad de persistencia al iniciar la app. */
  @PostConstruct
  public void init() {
    entityManagerFactory =
        Persistence.createEntityManagerFactory("com.carlosarroyoam.user-service");
  }

  /** Cierra el {@link EntityManagerFactory} si sigue abierto al detener la aplicación. */
  @PreDestroy
  public void destroy() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }

  /**
   * Crea un {@link EntityManager} para la petición en curso.
   *
   * @return el {@link EntityManager} de la petición
   */
  @Produces
  @RequestScoped
  public EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  /**
   * Cierra el {@link EntityManager} al terminar la petición si sigue abierto.
   *
   * @param entityManager el {@link EntityManager} a liberar
   */
  public void disposeEntityManager(@Disposes EntityManager entityManager) {
    if (entityManager.isOpen()) {
      entityManager.close();
    }
  }
}

package com.carlosarroyoam.user.service.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProducer {
  @Produces
  @RequestScoped
  public EntityManager createEntityManager() {
    return Persistence.createEntityManagerFactory("com.carlosarroyoam.user-service")
        .createEntityManager();
  }

  public void disposeEntityManager(@Disposes EntityManager entityManager) {
    if (entityManager.isOpen()) {
      entityManager.close();
    }
  }
}

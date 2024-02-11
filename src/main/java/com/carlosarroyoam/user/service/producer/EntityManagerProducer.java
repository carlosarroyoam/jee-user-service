package com.carlosarroyoam.user.service.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	@Produces
	public EntityManager createEntityManager() {
		return Persistence.createEntityManagerFactory("com.carlosarroyoam.user-service").createEntityManager();
	}

	public void disposeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}

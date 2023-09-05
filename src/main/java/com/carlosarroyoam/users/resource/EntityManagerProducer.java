package com.carlosarroyoam.users.resource;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@RequestScoped
public class EntityManagerProducer {

	@Produces
	public EntityManager createEntityManager() {
		return Persistence.createEntityManagerFactory("com.carlosarroyoam.users").createEntityManager();
	}

	public void disposeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}

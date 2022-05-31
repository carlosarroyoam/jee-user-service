package com.carlosarroyoam.users.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * CDI producer bean to manage {@link EntityManager} instances for our
 * persistence unit.
 */
@ApplicationScoped
public class EntityManagerProducer {
	/**
	 * Create an {@link EntityManager} instance for our persistence unit and make it
	 * injectable as a CDI bean.
	 *
	 * @return the instance
	 */
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		return Persistence.createEntityManagerFactory("com.carlosarroyoam.users").createEntityManager();
	}

	/**
	 * Dispose and close the given {@link EntityManager} instance.
	 *
	 * @param entityManager the instance to close
	 */
	public void disposeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}

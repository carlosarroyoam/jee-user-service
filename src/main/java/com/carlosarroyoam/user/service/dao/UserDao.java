package com.carlosarroyoam.user.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.carlosarroyoam.user.service.entity.User;

@ApplicationScoped
public class UserDao {

	@Inject
	private Logger logger;

	@Inject
	private EntityManager entityManager;

	public List<User> findAll() {
		logger.info("Find all users");
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_ALL, User.class);
		return query.getResultList();
	}

	public Optional<User> findById(Long userId) {
		logger.log(Level.INFO, "Find user with id: {0}", userId);
		User userById = entityManager.find(User.class, userId);
		return Optional.ofNullable(userById);
	}

	public Optional<User> findByUsername(String username) {
		logger.log(Level.INFO, "Find user with username: {0}", username);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);
		List<User> results = query.setParameter("username", username).getResultList();

		if (results.isEmpty())
			return Optional.empty();

		return Optional.of(results.get(0));
	}

	public Optional<User> findByEmail(String email) {
		logger.log(Level.INFO, "Find user with mail: {0}", email);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class);
		List<User> results = query.setParameter("email", email).getResultList();

		if (results.isEmpty())
			return Optional.empty();

		return Optional.of(results.get(0));
	}

	public void create(User user) {
		try {
			logger.log(Level.INFO, "Create user: {0}", user);
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			throw e;
		}
	}

	public void update(User user) {
		try {
			logger.log(Level.INFO, "Update user with id: {0}", user.getId());
			entityManager.getTransaction().begin();
			entityManager.merge(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			throw e;
		}
	}

	public void deleteById(Long userId) {
		try {
			logger.log(Level.INFO, "Delete user with id: {0}", userId);
			entityManager.getTransaction().begin();
			User user = entityManager.getReference(User.class, userId);
			entityManager.remove(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			throw e;
		}
	}

}

package com.carlosarroyoam.user.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.carlosarroyoam.user.service.entity.User;

@ApplicationScoped
@Transactional
public class UserDao {

	private final Logger logger;
	private final EntityManager entityManager;

	public UserDao(Logger logger, EntityManager entityManager) {
		this.logger = logger;
		this.entityManager = entityManager;
	}

	public List<User> findAll() {
		logger.info("Find all users");
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_ALL, User.class);
		return query.getResultList();
	}

	public Optional<User> findById(Long userId) {
		logger.log(Level.INFO, "Find user with id: {0}", userId);
		User findById = entityManager.getReference(User.class, userId);
		return Optional.ofNullable(findById);
	}

	public Optional<User> findByUsername(String username) {
		logger.log(Level.INFO, "Find user with username: {0}", username);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);
		User findByUsername = query.setParameter("username", username).getSingleResult();
		return Optional.ofNullable(findByUsername);
	}

	public Optional<User> findByEmail(String email) {
		logger.log(Level.INFO, "Find user with mail: {0}", email);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class);
		query.setParameter("email", email);
		User findByEmail = entityManager.getReference(User.class, email);
		return Optional.ofNullable(findByEmail);
	}

	public User create(User user) {
		logger.log(Level.INFO, "Create user: {0}", user);
		entityManager.persist(user);
		return user;
	}

	public User update(Long userId, User user) {
		logger.log(Level.INFO, "Update user with id: {0}", userId);
		entityManager.merge(user);
		return user;
	}

	public void delete(Long userId) {
		logger.log(Level.INFO, "Delete user with id: {0}", userId);
		User user = entityManager.getReference(User.class, userId);
		entityManager.remove(user);
	}

}

package com.carlosarroyoam.users.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.carlosarroyoam.users.entity.User;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserDao {

	@Inject
	private static Logger logger;

	@Inject
	private EntityManager entityManager;

	public Collection<User> findAll() {
		logger.info("Find all users.");
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_ALL, User.class);
		return Collections.unmodifiableCollection(query.getResultList());
	}

	public User findById(Long id) {
		logger.log(Level.INFO, "Find user with id: {0}.", id);
		return entityManager.getReference(User.class, id);
	}

	public User findByUsername(String username) {
		logger.log(Level.INFO, "Find user with username: {0}.", username);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

	public User findByEmail(String email) {
		logger.log(Level.INFO, "Find user with mail: {0}.", email);
		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class);
		query.setParameter("email", email);
		return entityManager.getReference(User.class, email);
	}

	public User create(User user) {
		logger.log(Level.INFO, "Creating {0}.", user);
		entityManager.persist(user);
		return user;
	}

	public User update(Long id, User user) {
		logger.log(Level.INFO, "Updating {0} with id: {1}.", new Object[] { user, id });
		entityManager.merge(user);
		return user;
	}

	public void delete(Long id) {
		logger.log(Level.INFO, "Deleting user with id: {0}.", id);
		User user = entityManager.getReference(User.class, id);
		entityManager.remove(user);
	}

}

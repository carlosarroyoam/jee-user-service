package com.carlosarroyoam.users.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.carlosarroyoam.users.dao.UserDao;
import com.carlosarroyoam.users.entity.User;

@ApplicationScoped
public class UserService {

	@Inject
	private static Logger logger;

	@Inject
	private UserDao userDao;

	public Collection<User> findAll() {
		return userDao.findAll();
	}

	public User findById(Long id) {
		return userDao.findById(id);
	}

	public User create(User user) {
		User findByUsername = userDao.findByUsername(user.getUsername());

		if (findByUsername != null) {
			logger.warning("Username already exists.");
			throw new BadRequestException("Username already exists.");
		}

		User findByEmail = userDao.findByEmail(user.getEmail());

		if (findByEmail != null) {
			logger.warning("Email already exists.");
			throw new BadRequestException("Email already exists.");
		}

		return userDao.create(user);
	}

	public User update(Long id, User user) {
		User findById = userDao.findById(id);

		if (findById == null) {
			logger.warning("User not exists.");
			throw new NotFoundException("User not exists.");
		}

		User findByUsername = userDao.findByUsername(user.getUsername());

		if (!findByUsername.getId().equals(id)) {
			logger.warning("Username already exists.");
			throw new BadRequestException("Username already exists.");
		}

		User findByEmail = userDao.findByEmail(user.getEmail());

		if (!findByEmail.getId().equals(id)) {
			logger.warning("Email already exists.");
			throw new BadRequestException("Email already exists.");
		}

		return userDao.update(id, user);
	}

	public void delete(Long id) {
		User findById = userDao.findById(id);

		if (findById == null) {
			logger.warning("User not exists.");
			throw new NotFoundException("User not exists.");
		}

		userDao.delete(id);
	}

}

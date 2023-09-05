package com.carlosarroyoam.users.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.carlosarroyoam.users.constants.AppMessages;
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
		User userById = userDao.findById(id);

		if (userById == null) {
			logger.warning(AppMessages.USER_NOT_EXISTS_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_EXISTS_EXCEPTION);
		}

		return userById;
	}

	public User create(User user) {
		User userByUsername = userDao.findByUsername(user.getUsername());

		if (userByUsername != null) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		User userByEmail = userDao.findByEmail(user.getEmail());

		if (userByEmail != null) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		return userDao.create(user);
	}

	public User update(Long id, User user) {
		User userById = userDao.findById(id);

		if (userById == null) {
			logger.warning(AppMessages.USER_NOT_EXISTS_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_EXISTS_EXCEPTION);
		}

		User userByUsername = userDao.findByUsername(user.getUsername());

		if (!userByUsername.getId().equals(id)) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		User userByEmail = userDao.findByEmail(user.getEmail());

		if (!userByEmail.getId().equals(id)) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		return userDao.update(id, user);
	}

	public void delete(Long id) {
		User userById = userDao.findById(id);

		if (userById == null) {
			logger.warning(AppMessages.USER_NOT_EXISTS_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_EXISTS_EXCEPTION);
		}

		userDao.delete(id);
	}

}

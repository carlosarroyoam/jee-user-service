package com.carlosarroyoam.user.service.service;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.carlosarroyoam.user.service.constants.AppMessages;
import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.entity.User;

@ApplicationScoped
public class UserService {

	private final Logger logger;
	private final UserDao userDao;

	public UserService(Logger logger, UserDao userDao) {
		this.logger = logger;
		this.userDao = userDao;
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public User findById(Long userId) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		return userById;
	}

	public User create(User user) {
		boolean existsWithUsername = userDao.findByUsername(user.getUsername()).isPresent();
		if (existsWithUsername) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		boolean existsWithEmail = userDao.findByEmail(user.getEmail()).isPresent();
		if (existsWithEmail) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		return userDao.create(user);
	}

	public User update(Long userId, User user) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		boolean existsWithUsername = userDao.findByUsername(user.getUsername()).isPresent();
		if (existsWithUsername) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		boolean existsWithEmail = userDao.findByEmail(user.getEmail()).isPresent();
		if (existsWithEmail) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		return userDao.update(userById.getId(), user);
	}

	public void delete(Long userId) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		userDao.delete(userById.getId());
	}

}

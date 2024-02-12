package com.carlosarroyoam.user.service.service;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.carlosarroyoam.user.service.constants.AppMessages;
import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.UserResponse;
import com.carlosarroyoam.user.service.entity.User;
import com.carlosarroyoam.user.service.mapper.UserMapper;

@ApplicationScoped
public class UserService {

	@Inject
	private Logger logger;

	@Inject
	private UserDao userDao;

	@Inject
	private UserMapper userMapper;

	public List<UserResponse> findAll() {
		List<User> users = userDao.findAll();
		return userMapper.toDtos(users);
	}

	public UserResponse findById(Long userId) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		return userMapper.toDto(userById);
	}

	public UserResponse create(User user) {
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

		userDao.create(user);
		return userMapper.toDto(user);
	}

	public UserResponse update(Long userId, User user) {
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

		userDao.update(user);
		return userMapper.toDto(user);
	}

	public void delete(Long userId) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		userDao.delete(userById.getId());
	}

}

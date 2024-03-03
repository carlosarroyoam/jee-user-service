package com.carlosarroyoam.user.service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.carlosarroyoam.user.service.constant.AppMessages;
import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.CreateUserRequest;
import com.carlosarroyoam.user.service.dto.UpdateUserRequest;
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

	@Transactional
	public UserResponse create(CreateUserRequest createUserRequest) {
		boolean existsByUsername = userDao.findByUsername(createUserRequest.getUsername()).isPresent();
		if (existsByUsername) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		boolean existsByEmail = userDao.findByEmail(createUserRequest.getEmail()).isPresent();
		if (existsByEmail) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		LocalDateTime now = LocalDateTime.now();
		User user = userMapper.toEntity(createUserRequest);

		user.setIsActive(Boolean.FALSE);
		user.setCreatedAt(now);
		user.setUpdatedAt(now);

		userDao.create(user);
		return userMapper.toDto(user);
	}

	@Transactional
	public void update(Long userId, UpdateUserRequest updateUserRequest) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		Optional<User> userByUsername = userDao.findByUsername(updateUserRequest.getUsername());
		if (userByUsername.isPresent() && !userByUsername.get().getId().equals(userId)) {
			logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
		}

		Optional<User> userByEmail = userDao.findByEmail(updateUserRequest.getEmail());
		if (userByEmail.isPresent() && !userByEmail.get().getId().equals(userId)) {
			logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
			throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
		}

		userById.setName(updateUserRequest.getName());
		userById.setAge(updateUserRequest.getAge());
		userById.setUsername(updateUserRequest.getUsername());
		userById.setEmail(updateUserRequest.getEmail());
		userById.setUpdatedAt(LocalDateTime.now());

		userDao.update(userById);
	}

	@Transactional
	public void deleteById(Long userId) {
		User userById = userDao.findById(userId).orElseThrow(() -> {
			logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
			throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
		});

		userDao.deleteById(userById.getId());
	}

}

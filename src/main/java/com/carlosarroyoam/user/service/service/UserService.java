package com.carlosarroyoam.user.service.service;

import com.carlosarroyoam.user.service.constant.AppMessages;
import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.CreateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UpdateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UserDto;
import com.carlosarroyoam.user.service.entity.User;
import com.carlosarroyoam.user.service.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {
  @Inject
  private Logger logger;

  @Inject
  private UserDao userDao;

  @Inject
  private UserMapper userMapper;

  public List<UserDto> findAll() {
    List<User> users = userDao.findAll();
    return userMapper.toDtos(users);
  }

  public UserDto findById(Long userId) {
    User userById = userDao.findById(userId).orElseThrow(() -> {
      logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
      throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
    });

    return userMapper.toDto(userById);
  }

  public UserDto create(CreateUserRequestDto requestDto) {
    if (userDao.findByUsername(requestDto.getUsername()).isPresent()) {
      logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
    }

    if (userDao.findByEmail(requestDto.getEmail()).isPresent()) {
      logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
    }

    LocalDateTime now = LocalDateTime.now();
    User user = userMapper.toEntity(requestDto);
    user.setIsActive(Boolean.FALSE);
    user.setCreatedAt(now);
    user.setUpdatedAt(now);

    userDao.create(user);
    return userMapper.toDto(user);
  }

  public void update(Long userId, UpdateUserRequestDto requestDto) {
    User userById = userDao.findById(userId).orElseThrow(() -> {
      logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
      throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
    });

    Optional<User> userByUsername = userDao.findByUsername(requestDto.getUsername());
    if (userByUsername.isPresent() && !userByUsername.get().getId().equals(userId)) {
      logger.warning(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(AppMessages.USERNAME_ALREADY_EXISTS_EXCEPTION);
    }

    Optional<User> userByEmail = userDao.findByEmail(requestDto.getEmail());
    if (userByEmail.isPresent() && !userByEmail.get().getId().equals(userId)) {
      logger.warning(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(AppMessages.EMAIL_ALREADY_EXISTS_EXCEPTION);
    }

    userById.setName(requestDto.getName());
    userById.setAge(requestDto.getAge());
    userById.setUsername(requestDto.getUsername());
    userById.setEmail(requestDto.getEmail());
    userById.setUpdatedAt(LocalDateTime.now());

    userDao.update(userById);
  }

  public void deleteById(Long userId) {
    User userById = userDao.findById(userId).orElseThrow(() -> {
      logger.warning(AppMessages.USER_NOT_FOUND_EXCEPTION);
      throw new NotFoundException(AppMessages.USER_NOT_FOUND_EXCEPTION);
    });

    userDao.deleteById(userById.getId());
  }
}

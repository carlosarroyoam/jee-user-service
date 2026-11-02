package com.carlosarroyoam.user.service.service;

import com.carlosarroyoam.user.service.constant.MessageConstants;
import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.CreateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UpdateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UserDto;
import com.carlosarroyoam.user.service.dto.UserDto.UserDtoMapper;
import com.carlosarroyoam.user.service.entity.User;
import com.carlosarroyoam.user.service.utils.PasswordUtils;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/** Lógica de negocio para consultar y administrar usuarios ({@link User}). */
@ApplicationScoped
public class UserService {
  @Inject private Logger logger;
  @Inject private UserDao userDao;
  @Inject private Clock clock;

  /**
   * Obtiene todos los usuarios.
   *
   * @return la lista de {@link UserDto} existentes
   */
  public List<UserDto> findAll() {
    List<User> users = userDao.findAll();
    return UserDtoMapper.INSTANCE.toDtos(users);
  }

  /**
   * Busca un usuario por su id.
   *
   * @param userId el id del usuario
   * @return el {@link UserDto} encontrado
   * @throws NotFoundException con 404 si no existe un usuario con ese id
   */
  public UserDto findById(Long userId) {
    User userById =
        userDao
            .findById(userId)
            .orElseThrow(() -> {
              logger.warning(MessageConstants.USER_NOT_FOUND_EXCEPTION);
              return new NotFoundException(MessageConstants.USER_NOT_FOUND_EXCEPTION);
            });

    return UserDtoMapper.INSTANCE.toDto(userById);
  }

  /**
   * Crea un usuario inactivo, almacena su contraseña como hash BCrypt y registra sus fechas de
   * creación y actualización.
   *
   * @param requestDto los datos del usuario a crear
   * @return el {@link UserDto} creado
   * @throws BadRequestException con 400 si ya existe un usuario con ese nombre de usuario o con ese
   *     correo electrónico
   */
  public UserDto create(CreateUserRequestDto requestDto) {
    if (userDao.findByUsername(requestDto.getUsername()).isPresent()) {
      logger.warning(MessageConstants.USERNAME_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(MessageConstants.USERNAME_ALREADY_EXISTS_EXCEPTION);
    }

    if (userDao.findByEmail(requestDto.getEmail()).isPresent()) {
      logger.warning(MessageConstants.EMAIL_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(MessageConstants.EMAIL_ALREADY_EXISTS_EXCEPTION);
    }

    LocalDateTime now = LocalDateTime.now(clock);
    User user = UserDtoMapper.INSTANCE.toEntity(requestDto);
    user.setPassword(PasswordUtils.hashPassword(requestDto.getPassword()));
    user.setIsActive(Boolean.FALSE);
    user.setCreatedAt(now);
    user.setUpdatedAt(now);

    userDao.create(user);
    return UserDtoMapper.INSTANCE.toDto(user);
  }

  /**
   * Actualiza el nombre, edad, correo electrónico y nombre de usuario de un usuario.
   *
   * @param userId el id del usuario a actualizar
   * @param requestDto los nuevos datos del usuario
   * @throws NotFoundException con 404 si no existe un usuario con ese id
   * @throws BadRequestException con 400 si otro usuario ya tiene ese nombre de usuario o ese correo
   *     electrónico
   */
  public void update(Long userId, UpdateUserRequestDto requestDto) {
    User userById =
        userDao
            .findById(userId)
            .orElseThrow(() -> {
              logger.warning(MessageConstants.USER_NOT_FOUND_EXCEPTION);
              return new NotFoundException(MessageConstants.USER_NOT_FOUND_EXCEPTION);
            });

    Optional<User> userByUsername = userDao.findByUsername(requestDto.getUsername());
    if (userByUsername.isPresent() && !userByUsername.get().getId().equals(userId)) {
      logger.warning(MessageConstants.USERNAME_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(MessageConstants.USERNAME_ALREADY_EXISTS_EXCEPTION);
    }

    Optional<User> userByEmail = userDao.findByEmail(requestDto.getEmail());
    if (userByEmail.isPresent() && !userByEmail.get().getId().equals(userId)) {
      logger.warning(MessageConstants.EMAIL_ALREADY_EXISTS_EXCEPTION);
      throw new BadRequestException(MessageConstants.EMAIL_ALREADY_EXISTS_EXCEPTION);
    }

    LocalDateTime now = LocalDateTime.now(clock);
    userById.setName(requestDto.getName());
    userById.setAge(requestDto.getAge());
    userById.setUsername(requestDto.getUsername());
    userById.setEmail(requestDto.getEmail());
    userById.setUpdatedAt(now);

    userDao.update(userById);
  }

  /**
   * Elimina un usuario por su id.
   *
   * @param userId el id del usuario a eliminar
   * @throws NotFoundException con 404 si no existe un usuario con ese id
   */
  public void deleteById(Long userId) {
    User userById =
        userDao
            .findById(userId)
            .orElseThrow(() -> {
              logger.warning(MessageConstants.USER_NOT_FOUND_EXCEPTION);
              return new NotFoundException(MessageConstants.USER_NOT_FOUND_EXCEPTION);
            });

    userDao.deleteById(userById.getId());
  }
}

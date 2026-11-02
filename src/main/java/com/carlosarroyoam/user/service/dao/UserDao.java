package com.carlosarroyoam.user.service.dao;

import com.carlosarroyoam.user.service.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/** Acceso a datos para la entidad {@link User}. */
@ApplicationScoped
public class UserDao {
  @Inject private Logger logger;
  @Inject private EntityManager entityManager;

  /**
   * Obtiene todos los usuarios.
   *
   * @return la lista de {@link User} existentes
   */
  public List<User> findAll() {
    logger.info("Find all users");
    TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_ALL, User.class);
    return query.getResultList();
  }

  /**
   * Busca un usuario por su id.
   *
   * @param userId el id del usuario
   * @return el usuario encontrado, o {@link Optional#empty()} si no existe ninguno con ese id
   */
  public Optional<User> findById(Long userId) {
    logger.log(Level.INFO, "Find user with id: {0}", userId);
    User userById = entityManager.find(User.class, userId);
    return Optional.ofNullable(userById);
  }

  /**
   * Busca un usuario por su nombre de usuario.
   *
   * @param username el nombre de usuario
   * @return el usuario encontrado, o {@link Optional#empty()} si no existe ninguno con ese nombre
   *     de usuario
   */
  public Optional<User> findByUsername(String username) {
    logger.log(Level.INFO, "Find user with username: {0}", username);
    TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);
    List<User> results = query.setParameter("username", username).getResultList();

    if (results.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(results.get(0));
  }

  /**
   * Busca un usuario por su correo electrónico.
   *
   * @param email el correo electrónico del usuario
   * @return el usuario encontrado, o {@link Optional#empty()} si no existe ninguno con ese correo
   */
  public Optional<User> findByEmail(String email) {
    logger.log(Level.INFO, "Find user with mail: {0}", email);
    TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class);
    List<User> results = query.setParameter("email", email).getResultList();

    if (results.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(results.get(0));
  }

  /**
   * Persiste un usuario dentro de una transacción. Si la operación falla, revierte la transacción y
   * relanza la excepción.
   *
   * @param user el usuario a persistir
   */
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

  /**
   * Actualiza un usuario dentro de una transacción. Si la operación falla, revierte la transacción
   * y relanza la excepción.
   *
   * @param user el usuario con los datos actualizados
   */
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

  /**
   * Elimina un usuario por su id dentro de una transacción. Si la operación falla, revierte la
   * transacción y relanza la excepción.
   *
   * @param userId el id del usuario a eliminar
   */
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

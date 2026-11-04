package com.carlosarroyoam.user.service.dao;

import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Ejecuta operaciones de persistencia dentro de una transacción {@code RESOURCE_LOCAL}.
 *
 * <p>Centraliza el ciclo de vida de la transacción (begin, commit y rollback) para que los DAO no
 * lo repitan. Usa el {@link EntityManager} de la petición en curso, por lo que cada llamada opera
 * sobre la transacción de esa petición.
 *
 * <p>Las operaciones no deben abrir ni cerrar la transacción por su cuenta, y no admiten
 * transacciones anidadas: si ya hay una activa, {@link EntityTransaction#begin()} lanza {@link
 * IllegalStateException} sin afectar a la transacción existente.
 */
@ApplicationScoped
public class TransactionExecutor {
  @Inject private EntityManager entityManager;

  /**
   * Ejecuta una operación sin resultado dentro de una transacción.
   *
   * @param work la operación a ejecutar
   * @throws RuntimeException la excepción lanzada por la operación o por el commit, tras revertir
   *     la transacción
   * @see #call(Supplier)
   */
  public void run(Runnable work) {
    call(() -> {
      work.run();
      return null;
    });
  }

  /**
   * Ejecuta una operación dentro de una transacción y devuelve su resultado.
   *
   * <p>Abre la transacción antes de ejecutar la operación y confirma al terminar. Si la operación o
   * el commit fallan con una {@link RuntimeException} o un {@link Error}, revierte la transacción y
   * relanza la excepción original. Si el rollback también falla, su excepción se adjunta a la
   * original como suprimida (ver {@link Throwable#getSuppressed()}).
   *
   * <p>{@link EntityTransaction#begin()} se invoca fuera del {@code try}: si falla porque ya había
   * una transacción activa, no se hace rollback de una transacción que este método no abrió.
   *
   * @param <T> el tipo del resultado
   * @param work la operación a ejecutar
   * @return el resultado de la operación
   * @throws RuntimeException la excepción lanzada por la operación o por el commit, tras revertir
   *     la transacción
   */
  public <T> T call(Supplier<T> work) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();

    try {
      T result = work.get();
      transaction.commit();
      return result;
    } catch (RuntimeException | Error e) {
      rollbackQuietly(transaction, e);
      throw e;
    }
  }

  /**
   * Revierte la transacción si sigue activa, sin dejar que un fallo del rollback oculte la causa
   * original.
   *
   * @param transaction la transacción a revertir
   * @param cause la excepción que provocó el rollback, a la que se adjunta cualquier fallo del
   *     rollback como suprimido
   */
  private void rollbackQuietly(EntityTransaction transaction, Throwable cause) {
    if (!transaction.isActive()) {
      return;
    }

    try {
      transaction.rollback();
    } catch (RuntimeException rollbackException) {
      cause.addSuppressed(rollbackException);
    }
  }
}

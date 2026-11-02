package com.carlosarroyoam.user.service.utils;

import com.carlosarroyoam.user.service.constant.CommonConstants;
import org.mindrot.jbcrypt.BCrypt;

/** Utilidades para generar y verificar hashes de contraseñas con BCrypt. */
public class PasswordUtils {
  /** Constructor privado que impide instanciar la clase. */
  private PasswordUtils() {
    throw new IllegalAccessError(CommonConstants.ILLEGAL_ACCESS_ERROR);
  }

  /**
   * Genera el hash BCrypt de una contraseña con un factor de costo de 12.
   *
   * @param plainPassword la contraseña en texto plano
   * @return el hash BCrypt de la contraseña
   */
  public static String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
  }

  /**
   * Verifica si una contraseña en texto plano coincide con un hash BCrypt.
   *
   * @param plainPassword la contraseña en texto plano
   * @param hashedPassword el hash BCrypt contra el que se compara
   * @return {@code true} si la contraseña coincide con el hash; {@code false} si no coincide o si
   *     alguno de los argumentos es {@code null}
   */
  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    if (plainPassword == null || hashedPassword == null) {
      return false;
    }
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}

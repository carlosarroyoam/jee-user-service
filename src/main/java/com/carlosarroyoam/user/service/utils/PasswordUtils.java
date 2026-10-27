package com.carlosarroyoam.user.service.utils;

import com.carlosarroyoam.user.service.constant.CommonConstants;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
  private PasswordUtils() {
    throw new IllegalAccessError(CommonConstants.ILLEGAL_ACCESS_ERROR);
  }

  public static String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
  }

  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    if (plainPassword == null || hashedPassword == null) {
      return false;
    }
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}

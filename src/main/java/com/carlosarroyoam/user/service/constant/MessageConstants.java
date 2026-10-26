package com.carlosarroyoam.user.service.constant;

public class MessageConstants {
  public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
  public static final String EMAIL_ALREADY_EXISTS_EXCEPTION = "Email already exists";
  public static final String USERNAME_ALREADY_EXISTS_EXCEPTION = "Username already exists";

  private MessageConstants() {
    throw new IllegalAccessError(CommonConstants.ILLEGAL_ACCESS_EXCEPTION);
  }
}

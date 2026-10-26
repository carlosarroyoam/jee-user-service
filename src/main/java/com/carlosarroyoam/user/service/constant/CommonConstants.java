package com.carlosarroyoam.user.service.constant;

public class CommonConstants {
  public static final String ILLEGAL_ACCESS_EXCEPTION = "Illegal access to utility class";

  private CommonConstants() {
    throw new IllegalAccessError(ILLEGAL_ACCESS_EXCEPTION);
  }
}

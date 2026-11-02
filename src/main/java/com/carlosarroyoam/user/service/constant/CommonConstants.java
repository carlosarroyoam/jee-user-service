package com.carlosarroyoam.user.service.constant;

/** Constantes comunes compartidas por toda la aplicación. */
public class CommonConstants {
  public static final String ILLEGAL_ACCESS_ERROR = "Illegal access to utility class";

  /** Constructor privado que impide instanciar la clase. */
  private CommonConstants() {
    throw new IllegalAccessError(ILLEGAL_ACCESS_ERROR);
  }
}

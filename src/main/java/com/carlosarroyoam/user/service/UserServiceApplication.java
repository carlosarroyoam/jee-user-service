package com.carlosarroyoam.user.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/** Configura la aplicación JAX-RS y publica los recursos REST bajo {@code /api/v1}. */
@ApplicationPath("/api/v1")
public class UserServiceApplication extends Application {}

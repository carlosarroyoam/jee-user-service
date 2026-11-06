package com.carlosarroyoam.user.service;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/** Configura la aplicación JAX-RS y publica los recursos REST bajo {@code /api/v1}. */
@ApplicationPath("/api/v1")
public class UserServiceApplication extends Application {}

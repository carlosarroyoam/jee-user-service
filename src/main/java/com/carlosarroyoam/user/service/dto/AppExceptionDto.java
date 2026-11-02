package com.carlosarroyoam.user.service.dto;

import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** Representa el cuerpo de error devuelto por la API cuando una petición falla. */
@Getter
@Setter
@Builder
public class AppExceptionDto {
  private String message;
  private Set<String> details;
  private Integer code;
  private String status;
  private String path;
  private ZonedDateTime timestamp;
}

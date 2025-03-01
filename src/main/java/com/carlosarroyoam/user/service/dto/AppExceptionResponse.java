package com.carlosarroyoam.user.service.dto;

import java.time.ZonedDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppExceptionResponse {
  private String message;
  private Set<String> details;
  private Integer code;
  private String status;
  private String path;
  private ZonedDateTime timestamp;
}

package com.carlosarroyoam.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
  private Integer id;
  private String title;
  private String description;
}

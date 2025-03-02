package com.carlosarroyoam.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {
  @NotBlank(message = "Name should not be blank")
  @Size(min = 3, max = 128, message = "Name should be between 3 and 128")
  private String name;

  @NotNull(message = "Age should not be null")
  @Min(message = "Age should be min 18", value = 18)
  @Max(message = "Age should be max 100", value = 100)
  private Byte age;

  @NotBlank(message = "Email should not be blank")
  @Email(message = "Email should be an valid email address")
  @Size(min = 3, max = 128, message = "Email should be between 3 and 128")
  private String email;

  @NotBlank(message = "Username should not be blank")
  @Size(min = 3, max = 128, message = "Email should be between 3 and 128")
  private String username;

  @NotBlank(message = "Password should not be blank")
  @Size(min = 3, max = 32, message = "Password should be between 3 and 32")
  private String password;

  @NotNull(message = "Role_id should not be null")
  private Integer roleId;
}

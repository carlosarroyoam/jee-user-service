package com.carlosarroyoam.user.service.dto;

import com.carlosarroyoam.user.service.entity.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
  private Integer id;
  private String title;
  private String description;

  @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public interface RoleDtoMapper {
    RoleDtoMapper INSTANCE = Mappers.getMapper(RoleDtoMapper.class);

    RoleDto toDto(Role role);

    List<RoleDto> toDtos(List<Role> roles);
  }
}

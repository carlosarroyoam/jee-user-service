package com.carlosarroyoam.user.service.dto;

import com.carlosarroyoam.user.service.entity.Role;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
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

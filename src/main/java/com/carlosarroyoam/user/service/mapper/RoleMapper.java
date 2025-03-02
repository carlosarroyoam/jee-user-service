package com.carlosarroyoam.user.service.mapper;

import com.carlosarroyoam.user.service.dto.RoleDto;
import com.carlosarroyoam.user.service.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
  RoleDto toDto(Role role);

  List<RoleDto> toDtos(List<Role> roles);
}

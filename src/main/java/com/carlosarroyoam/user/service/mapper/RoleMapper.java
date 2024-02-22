package com.carlosarroyoam.user.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.carlosarroyoam.user.service.dto.RoleResponse;
import com.carlosarroyoam.user.service.entity.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

	RoleResponse toDto(Role role);

	List<RoleResponse> toDtos(List<Role> roles);

}

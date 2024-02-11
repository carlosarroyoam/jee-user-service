package com.carlosarroyoam.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.carlosarroyoam.user.service.dto.UserResponse;
import com.carlosarroyoam.user.service.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserResponse toDto(User user);

	List<UserResponse> toDtos(List<User> users);

}

package com.carlosarroyoam.user.service.mapper;

import com.carlosarroyoam.user.service.dto.CreateUserRequest;
import com.carlosarroyoam.user.service.dto.UserResponse;
import com.carlosarroyoam.user.service.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  UserResponse toDto(User user);

  List<UserResponse> toDtos(List<User> users);

  User toEntity(CreateUserRequest createUserRequest);
}

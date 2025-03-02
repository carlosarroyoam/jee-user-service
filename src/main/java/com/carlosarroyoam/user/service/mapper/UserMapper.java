package com.carlosarroyoam.user.service.mapper;

import com.carlosarroyoam.user.service.dto.CreateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UserDto;
import com.carlosarroyoam.user.service.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  UserDto toDto(User user);

  List<UserDto> toDtos(List<User> users);

  User toEntity(CreateUserRequestDto requestDto);
}

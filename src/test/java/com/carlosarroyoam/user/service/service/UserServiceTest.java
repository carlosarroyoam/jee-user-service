package com.carlosarroyoam.user.service.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.UserDto;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private Logger logger;

  @Mock
  private UserDao userDao;

  @InjectMocks
  private UserService userService;

  @Test
  void shouldReturnListOfUsers() {
    when(userDao.findAll()).thenReturn(Collections.emptyList());

    List<UserDto> users = userService.findAll();

    assertThat(users, is(notNullValue()));
    assertThat(users.size(), is(0));
  }
}

package com.carlosarroyoam.user.service.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import com.carlosarroyoam.user.service.dao.UserDao;
import com.carlosarroyoam.user.service.dto.UserDto;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock private UserDao userDao;
  @Mock private Logger logger;
  @Spy private Clock clock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
  @InjectMocks private UserService userService;

  @Test
  void shouldReturnListOfUsers() {
    when(userDao.findAll()).thenReturn(Collections.emptyList());

    List<UserDto> users = userService.findAll();

    assertThat(users, is(notNullValue()));
    assertThat(users.size(), is(0));
  }
}

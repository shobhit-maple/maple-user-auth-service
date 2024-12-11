package com.maple.userauth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.alviss.commons.exception.NotFoundException;
import com.maple.userauth.dao.UserLoginDao;
import com.maple.userauth.service.model.UserLoginEntity;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserLoginServiceTest {

  private UserLoginService testUnit;

  @Mock private UserLoginDao dao;

  @BeforeEach
  public void beforeEach() {
    testUnit = new UserLoginServiceImpl(dao);
  }

  @Test
  public void testGet_notFound_throws() {
    final String testUserId = UUID.randomUUID().toString();
    final String testAccountId = UUID.randomUUID().toString();

    Exception exception =
        assertThrows(
            NotFoundException.class,
            () -> {
              testUnit.get(testUserId, testAccountId);
            });
    assertInstanceOf(NotFoundException.class, exception);
  }

  @Test
  public void testGetByEmailAddress_notFound_throws() {
    final String testEmailAddress = UUID.randomUUID() + "@gmail.com";

    Exception exception =
        assertThrows(
            NotFoundException.class,
            () -> {
              testUnit.getByEmailAddress(testEmailAddress);
            });
    assertInstanceOf(NotFoundException.class, exception);
  }
}

package com.maple.userauth.api.v1.response.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alviss.commons.api.model.Response;
import com.alviss.commons.exception.BadRequestException;
import com.alviss.commons.validator.CustomValidator;
import com.maple.userauth.api.v1.request.UserLoginRequest;
import com.maple.userauth.api.v1.response.UserLoginResponse;
import com.maple.userauth.service.UserLoginService;
import com.maple.userauth.service.UserLoginServiceImpl;
import com.maple.userauth.service.model.UserLoginEntity;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserLoginApiHandlerTest {

  private UserLoginApiHandler testUnit;

  @Mock
  private UserLoginService userLoginService;
  @Mock
  private UserLoginEntityMapper mapper;
  @Mock
  private CustomValidator validator;

  private UserLoginEntity mockEntity;
  private UserLoginRequest mockRequest;
  private UserLoginResponse mockResponse;

  @BeforeEach
  public void setUp() {
    testUnit = new UserLoginApiHandler(userLoginService, mapper, validator);
    mockEntity = mock(UserLoginEntity.class);
    mockRequest = mock(UserLoginRequest.class);
    mockResponse = mock(UserLoginResponse.class);
  }

  @Test
  public void testHandleGet() {
    final var id = UUID.randomUUID().toString();
    final var userAccountId = UUID.randomUUID().toString();
    when(userLoginService.get(id, userAccountId)).thenReturn(mockEntity);
    when(mapper.entityToResponse(mockEntity)).thenReturn(mockResponse);

    Response<UserLoginResponse> response = testUnit.handleGet(id, userAccountId);

    assertNotNull(response);
    assertEquals(mockResponse, response.getContent());
    verify(userLoginService).get(id, userAccountId);
    verify(mapper).entityToResponse(mockEntity);
  }

  @Test
  public void testHandlePost() {
    final var userAccountId = UUID.randomUUID().toString();
    when(mapper.mapForPost(userAccountId, mockRequest)).thenReturn(mockEntity);
    when(userLoginService.save(mockEntity)).thenReturn(mockEntity);
    when(mapper.entityToResponse(mockEntity)).thenReturn(mockResponse);
    when(validator.validate(mockEntity)).thenReturn(Collections.emptyList()); // No validation errors

    Response<UserLoginResponse> response = testUnit.handlePost(userAccountId, mockRequest);

    assertNotNull(response);
    assertEquals(mockResponse, response.getContent());
    verify(mapper).mapForPost(userAccountId, mockRequest);
    verify(userLoginService).save(mockEntity);
  }

  @Test
  public void testHandlePostWithValidationError() {
    final var userAccountId = UUID.randomUUID().toString();
    when(mapper.mapForPost(userAccountId, mockRequest)).thenReturn(mockEntity);
    when(validator.validate(mockEntity)).thenReturn(Collections.singletonList("Validation error"));

    BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
      testUnit.handlePost(userAccountId, mockRequest);
    });
    assertEquals("There are some validation errors", thrown.getMessage());
    verify(mapper).mapForPost(userAccountId, mockRequest);
    verify(userLoginService, never()).save(mockEntity); // Should not be called if validation fails
  }

  @Test
  public void testHandlePatch() {
    final var id = UUID.randomUUID().toString();
    final var userAccountId = UUID.randomUUID().toString();
    when(userLoginService.get(id, userAccountId)).thenReturn(mockEntity);
    when(mapper.mapForPatch(mockEntity, mockRequest)).thenReturn(mockEntity);
    when(userLoginService.save(mockEntity)).thenReturn(mockEntity);
    when(mapper.entityToResponse(mockEntity)).thenReturn(mockResponse);
    when(validator.validate(mockEntity)).thenReturn(Collections.emptyList()); // No validation errors

    Response<UserLoginResponse> response = testUnit.handlePatch(id, userAccountId, mockRequest);

    assertNotNull(response);
    assertEquals(mockResponse, response.getData());
    verify(userLoginService).get(id, userAccountId);
    verify(mapper).mapForPatch(mockEntity, mockRequest);
    verify(userLoginService).save(mockEntity);
  }

  @Test
  public void testHandlePut() {
    final var id = UUID.randomUUID().toString();
    final var userAccountId = UUID.randomUUID().toString();
    when(userLoginService.get(id, userAccountId)).thenReturn(mockEntity);
    when(mapper.mapForPut(mockEntity, mockRequest)).thenReturn(mockEntity);
    when(userLoginService.save(mockEntity)).thenReturn(mockEntity);
    when(mapper.entityToResponse(mockEntity)).thenReturn(mockResponse);
    when(validator.validate(mockEntity)).thenReturn(Collections.emptyList()); // No validation errors

    Response<UserLoginResponse> response = testUnit.handlePut(id, userAccountId, mockRequest);

    assertNotNull(response);
    assertEquals(mockResponse, response.getData());
    verify(userLoginService).get(id, userAccountId);
    verify(mapper).mapForPut(mockEntity, mockRequest);
    verify(userLoginService).save(mockEntity);
  }

  @Test
  public void testHandleDelete() {
    final var id = UUID.randomUUID().toString();
    final var userAccountId = UUID.randomUUID().toString();
    when(userLoginService.get(id, userAccountId)).thenReturn(mockEntity);

    testUnit.handleDelete(id, userAccountId);

    verify(userLoginService).get(id, userAccountId);
    verify(userLoginService).delete(mockEntity);
  }
}

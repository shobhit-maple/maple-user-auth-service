package com.maple.userauth.api.v1.response.builder;

import com.alviss.commons.api.model.Response;
import com.alviss.commons.exception.BadRequestException;
import com.alviss.commons.validator.CustomValidator;
import com.maple.userauth.api.v1.request.UserLoginRequest;
import com.maple.userauth.api.v1.response.UserLoginResponse;
import com.maple.userauth.service.UserLoginService;
import com.maple.userauth.service.model.UserLoginEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserLoginApiHandler {

  private final UserLoginService userLoginService;
  private final UserLoginEntityMapper mapper;
  private final CustomValidator validator;

  public Response<UserLoginResponse> handleGet(final String id, final String userAccountId) {
    val entity = userLoginService.get(id, userAccountId);
    return new Response<>(mapper.entityToResponse(entity));
  }

  public Response<UserLoginResponse> handlePost(final String userAccountId, final UserLoginRequest request) {
    val entity = mapper.mapForPost(userAccountId, request);
    validate(entity);
    return new Response<>(mapper.entityToResponse(userLoginService.save(entity)));
  }

  public Response<UserLoginResponse> handlePatch(
      final String id, final String userAccountId, final UserLoginRequest request) {
    val existingEntity = userLoginService.get(id, userAccountId);
    val updatedEntity = mapper.mapForPatch(existingEntity, request);
    validate(updatedEntity);
    return new Response<>(mapper.entityToResponse(userLoginService.save(updatedEntity)));
  }

  public Response<UserLoginResponse> handlePut(
      final String id, final String userAccountId, final UserLoginRequest request) {
    val existingEntity = userLoginService.get(id, userAccountId);
    val updatedEntity = mapper.mapForPut(existingEntity, request);
    validate(updatedEntity);
    return new Response<>(mapper.entityToResponse(userLoginService.save(updatedEntity)));
  }

  public void handleDelete(final String id, final String userAccountId) {
    val entity = userLoginService.get(id, userAccountId);
    userLoginService.delete(entity);
  }

  private void validate(final UserLoginEntity entity) {
    val errors = validator.validate(entity);
    if (!errors.isEmpty()) {
      throw new BadRequestException("There are some validation errors", errors);
    }
  }
}

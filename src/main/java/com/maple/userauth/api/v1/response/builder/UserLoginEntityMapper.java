package com.maple.userauth.api.v1.response.builder;

import com.maple.userauth.api.v1.request.UserLoginRequest;
import com.maple.userauth.api.v1.response.UserLoginResponse;
import com.maple.userauth.api.v1.response.UserLoginResponse.UserLoginData;
import com.maple.userauth.service.model.UserLoginEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserLoginEntityMapper {

  private final PasswordEncoder passwordEncoder;

  public UserLoginEntity mapForPost(
      final String userAccountId, final UserLoginRequest request) {
    return UserLoginEntity.builder()
        .organizationId(request.getOrganizationId())
        .userAccountId(userAccountId)
        .emailAddress(request.getEmailAddress())
        .passwordHash(passwordEncoder.encode(request.getPassword()))
        .build();
  }

  public UserLoginEntity mapForPut(
      final UserLoginEntity entity, final UserLoginRequest request) {
    entity.setEmailAddress(request.getEmailAddress());
    entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    return entity;
  }

  public UserLoginEntity mapForPatch(
      final UserLoginEntity entity, final UserLoginRequest request) {
    if (request.getEmailAddress() != null) {
      entity.setEmailAddress(request.getEmailAddress());
    }
    if (request.getPassword() != null) {
      entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    }
    return entity;
  }

  public UserLoginResponse entityToResponse(final UserLoginEntity entity) {
    return UserLoginResponse.builder()
        .id(entity.getId())
        .data(
            UserLoginData.builder()
                .organizationId(entity.getOrganizationId())
                .userAccountId(entity.getUserAccountId())
                .emailAddress(entity.getEmailAddress())
                .build())
        .build();
  }
}

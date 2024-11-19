package com.maple.userauth.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

  private String id;
  private UserLoginData data;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserLoginData {
    private String organizationId;
    private String userAccountId;
    private String emailAddress;
  }
}

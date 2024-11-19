package com.maple.userauth.api.v1.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// JWT response
public class AuthResponse {

  private String token;
  private String userId;
  private String organizationId;
  private String userAccountId;
  private String emailAddress;
  private List<String> roles;
}

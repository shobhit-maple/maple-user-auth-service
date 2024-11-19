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
public class SessionResponse {

  private String userId;
  private String organizationId;
  private String userAccountId;
  private List<String> roles;
}

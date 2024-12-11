package com.maple.userauth.api.v1.response.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alviss.commons.exception.BadRequestException;
import com.alviss.commons.validator.CustomValidator;
import com.maple.userauth.api.v1.request.AuthRequest;
import com.maple.userauth.api.v1.response.AuthResponse;
import com.maple.userauth.config.security.JwtHelper;
import com.maple.userauth.config.security.UserDetailsImpl;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class AuthApiHandlerTest {

  private AuthApiHandler testUnit;
  @Mock private CustomValidator validator;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtHelper jwtHelper;
  @Mock private Authentication authentication;
  @Mock private UserDetailsImpl userDetails;

  @BeforeEach
  void setUp() {
    testUnit = new AuthApiHandler(validator, authenticationManager, jwtHelper);
  }

  @Test
  void testHandleLogin_validationFailure() {
    final var request = new AuthRequest("user@example.com", "password");
    when(validator.validate(request))
        .thenReturn(
            Map.of(PathImpl.createPathFromString("emailAddress"), List.of("Invalid email address")));

    assertThrows(BadRequestException.class, () -> testUnit.handleLogin(request));
  }

  @Test
  void testHandleLogin_success() {
    final var request = new AuthRequest("user@example.com", "password");
    when(validator.validate(request)).thenReturn(Map.of());
    when(authenticationManager.authenticate(any())).thenReturn(authentication);
    when(jwtHelper.generateJwtToken(authentication)).thenReturn("mockToken");
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getId()).thenReturn("userId");
    when(userDetails.getOrganizationId()).thenReturn("orgId");
    when(userDetails.getUserAccountId()).thenReturn("accountId");
    when(userDetails.getEmailAddress()).thenReturn("user@example.com");
    when(userDetails.getAuthorities()).thenReturn(List.of());

    final var response = testUnit.handleLogin(request);

    assertNotNull(response);
    assertEquals("mockToken", response.getToken());
    assertEquals("userId", response.getUserId());
    assertEquals("orgId", response.getOrganizationId());
    assertEquals("accountId", response.getUserAccountId());
    assertEquals("user@example.com", response.getEmailAddress());
  }

  @Test
  void testHandleSession_success() {
    final var securityContext = mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getId()).thenReturn("userId");
    when(userDetails.getUserAccountId()).thenReturn("accountId");
    when(userDetails.getAuthorities()).thenReturn(List.of());

    final var response = testUnit.handleSession();

    assertNotNull(response);
    assertEquals("userId", response.getUserId());
    assertEquals("accountId", response.getUserAccountId());
  }
}

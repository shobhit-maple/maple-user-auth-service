package com.maple.userauth.api.v1.response.builder;

import com.alviss.commons.exception.BadRequestException;
import com.alviss.commons.validator.CustomValidator;
import com.maple.userauth.api.v1.request.AuthRequest;
import com.maple.userauth.api.v1.response.AuthResponse;
import com.maple.userauth.api.v1.response.SessionResponse;
import com.maple.userauth.config.security.JwtHelper;
import com.maple.userauth.config.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthApiHandler {

  private final CustomValidator validator;
  private final AuthenticationManager authenticationManager;
  private final JwtHelper jwtHelper;

  public AuthResponse handleLogin(final AuthRequest request) {
    val errors = validator.validate(request);
    if (!errors.isEmpty()) {
      throw new BadRequestException("There are some validation errors", errors);
    }

    val authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmailAddress(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    val jwtToken = jwtHelper.generateJwtToken(authentication);

    val userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return new AuthResponse(
        jwtToken,
        userDetails.getId(),
        userDetails.getOrganizationId(),
        userDetails.getUserAccountId(),
        userDetails.getEmailAddress(),
        roles);
  }

  public SessionResponse handleSession() {
    val authentication = SecurityContextHolder.getContext().getAuthentication();
    val userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return SessionResponse.builder()
        .userId(userDetails.getId())
        .userAccountId(userDetails.getUserAccountId())
        .roles(
            userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .build();
  }
}

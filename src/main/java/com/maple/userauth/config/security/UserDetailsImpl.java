package com.maple.userauth.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maple.userauth.service.model.UserLoginEntity;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  @Serial private static final long serialVersionUID = 1L;

  private final String id;
  private final String organizationId;
  private final String userAccountId;
  private final String emailAddress;
  @JsonIgnore private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public static UserDetailsImpl build(final UserLoginEntity user) {
    return new UserDetailsImpl(
        user.getId(),
        user.getOrganizationId(),
        user.getUserAccountId(),
        user.getEmailAddress(),
        user.getPasswordHash(),
        new ArrayList<>());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return emailAddress;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}

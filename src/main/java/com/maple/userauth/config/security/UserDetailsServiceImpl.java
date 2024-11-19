package com.maple.userauth.config.security;

import com.alviss.commons.exception.NotFoundException;
import com.maple.userauth.service.UserLoginService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserLoginService userLoginService;

  @Override
  public UserDetails loadUserByUsername(final String emailAddress)
      throws UsernameNotFoundException {
    val user = userLoginService.getByEmailAddress(emailAddress);
    return UserDetailsImpl.build(user);
  }
}

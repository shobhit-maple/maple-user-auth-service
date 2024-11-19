package com.maple.userauth.api.v1.controller;

import com.maple.userauth.api.v1.request.AuthRequest;
import com.maple.userauth.api.v1.response.AuthResponse;
import com.maple.userauth.api.v1.response.SessionResponse;
import com.maple.userauth.api.v1.response.builder.AuthApiHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AuthControllerV1")
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthApiHandler apiHandler;

  @PostMapping("/login")
  public AuthResponse login(@RequestBody final AuthRequest request) {
    return apiHandler.handleLogin(request);
  }

  @GetMapping("/session")
  public SessionResponse session() {
    return apiHandler.handleSession();
  }
}

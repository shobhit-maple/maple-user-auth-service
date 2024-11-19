package com.maple.userauth.api.v1.controller;

import com.alviss.commons.api.model.Response;
import com.alviss.commons.security.SecurityService;
import com.maple.userauth.api.v1.request.UserLoginRequest;
import com.maple.userauth.api.v1.response.UserLoginResponse;
import com.maple.userauth.api.v1.response.builder.UserLoginApiHandler;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserLoginControllerV1")
@AllArgsConstructor
@RequestMapping("/api/internal/v1/user-accounts/{userAccountId}/user-login")
public class UserLoginController {

  private final UserLoginApiHandler apiHandler;
  private final SecurityService securityService;

  @GetMapping("/{id}")
  //  @PreAuthorize("@securityService.hasPermission('ROLE_USER_GET')")
  public Response<UserLoginResponse> get(
      @PathVariable final String userAccountId, @PathVariable final String id) {
    return apiHandler.handleGet(id, userAccountId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  //  @PreAuthorize("@securityService.hasPermission('ROLE_USER_CREATE')")
  public Response<UserLoginResponse> post(
      @PathVariable final String userAccountId, @RequestBody final UserLoginRequest request) {
    return apiHandler.handlePost(userAccountId, request);
  }

  @PutMapping("/{id}")
  //  @PreAuthorize("@securityService.hasPermission('ROLE_USER_UPDATE')")
  public Response<UserLoginResponse> put(
      @PathVariable final String userAccountId,
      @PathVariable final String id,
      @RequestBody final UserLoginRequest request) {
    return apiHandler.handlePut(id, userAccountId, request);
  }

  @PatchMapping("/{id}")
  //  @PreAuthorize("@securityService.hasPermission('ROLE_USER_UPDATE')")
  public Response<UserLoginResponse> patch(
      @PathVariable final String userAccountId,
      @PathVariable final String id,
      @RequestBody final UserLoginRequest request) {
    return apiHandler.handlePatch(id, userAccountId, request);
  }

  @DeleteMapping("/{id}")
  //  @PreAuthorize("@securityService.hasPermission('ROLE_USER_DELETE')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final String userAccountId, @PathVariable final String id) {
    apiHandler.handleDelete(id, userAccountId);
  }
}

package com.maple.userauth.service;

import com.maple.userauth.service.model.UserLoginEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserLoginService {

  UserLoginEntity get(String id, String userAccountId);

  UserLoginEntity getByEmailAddress(String id);

  UserLoginEntity save(UserLoginEntity entity);

  void delete(UserLoginEntity entity);
}

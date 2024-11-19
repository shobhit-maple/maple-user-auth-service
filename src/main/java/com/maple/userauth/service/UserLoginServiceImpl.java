package com.maple.userauth.service;

import com.alviss.commons.exception.NotFoundException;
import com.maple.userauth.dao.UserLoginDao;
import com.maple.userauth.service.model.UserLoginEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

  private final UserLoginDao dao;

  @Override
  public UserLoginEntity get(final String id, final String userAccountId) {
    return dao.findByIdAndUserAccountId(id, userAccountId)
        .orElseThrow(() -> new NotFoundException("Requested user login is not found"));
  }

  @Override
  public UserLoginEntity getByEmailAddress(String emailAddress) {
    return dao.findByEmailAddress(emailAddress)
        .orElseThrow(() -> new NotFoundException("Requested user login is not found"));
  }

  @Override
  public UserLoginEntity save(final UserLoginEntity entity) {
    return dao.saveAndFlush(entity);
  }

  @Override
  public void delete(final UserLoginEntity entity) {
    dao.delete(entity);
  }
}

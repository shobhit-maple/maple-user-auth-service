package com.maple.userauth.dao;

import com.maple.userauth.service.model.UserLoginEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginDao
    extends JpaRepository<UserLoginEntity, String>, JpaSpecificationExecutor<UserLoginEntity> {

  Optional<UserLoginEntity> findByIdAndUserAccountId(String id, String userAccountId);

  Optional<UserLoginEntity> findByEmailAddress(final String emailAddress);
}

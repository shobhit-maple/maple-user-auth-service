package com.maple.userauth.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Container
  private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:latest");

  @Override
  public void initialize(final @NotNull ConfigurableApplicationContext applicationContext) {
    database.start();
    TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "spring.datasource.url=" + database.getJdbcUrl(),
        "spring.datasource.username=" + database.getUsername(),
        "spring.datasource.password=" + database.getPassword()
    );
  }
}
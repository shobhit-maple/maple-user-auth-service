package com.maple.userauth.config;

import com.alviss.commons.security.RequestAuthorityInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new RequestAuthorityInterceptor()).excludePathPatterns("/api/v1/auth/**");
  }
}
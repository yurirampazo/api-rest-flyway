package com.alura.api_rest.infra.config.annotations;

import com.alura.api_rest.infra.annotations.aspect.NormalizationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

  @Bean
  public NormalizationAspect normalizationAspect() {
    return new NormalizationAspect();
  }
}

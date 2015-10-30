package com.maxxton.mis.leave.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;

/**
 * Swagger configuration class. Swagger is being used to generate API docs.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Autowired
  private TypeResolver typeResolver;

  @Bean
  public Docket memoApi() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(paths()).build().apiInfo(apiInfo()).pathMapping("/")
        .directModelSubstitute(org.joda.time.LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false) // disable auto generating of responses for
                                                                                                                                                            // REST-endpoints
        .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)));
  }

  /**
   * Define the paths to include.
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  private Predicate<String> paths() {
    return or(regex("/mis.*"));
  }

  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfo("MIS Leave Service API", "MisLeaveService API", "1.0", null, "S. Kelkar (s.kelkar@maxxton.com)", null, null);
    return apiInfo;
  }

}

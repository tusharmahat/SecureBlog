package com.takeo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

//	http://localhost:8080/swagger-ui/index.html#/            Rest Documentation
	@Bean
	public GroupedOpenApi controllerApi() {
		return GroupedOpenApi.builder().group("controller-api").packagesToScan("com.takeo.rest").build();
	}

}
package com.takeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.takeo.service.UserDataDetailsService;
import com.takeo.utils.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {

		return new UserDataDetailsService();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/user/register", "/user/verify/**", "/user/login", "/user/forgotpassword/**", "/post")
				.permitAll().requestMatchers(HttpMethod.GET, "/post/**", "/comment/**").permitAll()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()//TODO: change to admin only

				.requestMatchers("/report/**", "/role/**", "/category/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.GET, "/user").hasAuthority("ADMIN")
				.requestMatchers("/user/password", "/user/pic/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/user/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/post/**", "/comment/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/post/**", "/comment/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/post/**", "/comment/**").authenticated().and().formLogin().and()
				.logout() // Add logout configuration
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID") // Delete cookies if any
				.and().exceptionHandling().and().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}

package com.dongyw.shinramyun.config;

import com.dongyw.shinramyun.config.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JwtTokenFilter jwtTokenFilter;
	private final CorsFilter corsFilter;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		  .httpBasic(AbstractHttpConfigurer::disable)
		  .csrf(AbstractHttpConfigurer::disable)
		  .cors(withDefaults()) // CORS 설정 추가
		  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		  .authorizeHttpRequests(request -> request
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
			.requestMatchers("/public/**").permitAll()
			.requestMatchers("/actuator/**").permitAll()
			.anyRequest().authenticated()
		  )
		  .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
		  .addFilterBefore(corsFilter, JwtTokenFilter.class) // CORS 필터 추가
		  .formLogin(AbstractHttpConfigurer::disable)
		;
		return httpSecurity.build();
	}

}

package com.dongyw.shinramyun.config.filter;

import com.dongyw.shinramyun.jwt.CustomerDetailService;
import com.dongyw.shinramyun.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final CustomerDetailService customerDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header != null) {
			String accessToken = header.split(" ")[1].trim();
			String email = jwtProvider.validateToken(accessToken);
			UsernamePasswordAuthenticationToken authentication;
			UserDetails customer = customerDetailService.loadUserByUsername(email);
			authentication = new UsernamePasswordAuthenticationToken(
			  customer, null, customer.getAuthorities()
			);
			authentication.setDetails(new WebAuthenticationDetailsSource());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getRequestURI().contains("public");
	}

}

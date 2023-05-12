package com.greenstitch.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("inside doFilter....");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {

			System.out.println("auth.getAuthorities " + authentication.getAuthorities());

			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

			String jwt = Jwts.builder().setIssuer("Subham").setSubject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities())).setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + 30000000)) // expiration time of 8 hours
					.signWith(key).compact();

			response.setHeader(SecurityConstants.JWT_HEADER, jwt);

		}

		filterChain.doFilter(request, response);

	}

	/**
	 * 
	 * Converts a collection of GrantedAuthority instances to a comma-separated
	 * string representation of their names.
	 * 
	 * @param collection the collection of GrantedAuthority instances
	 * 
	 * @return a comma-separated string of authority names
	 */
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

		Set<String> authoritiesSet = new HashSet<>();

		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);

	}

	/**
	 * 
	 * this make sure that this filter will execute only for first time when client
	 * call the api /login at first time
	 * 
	 * Indicates whether this filter should not filter the given request.
	 * <p>
	 * This implementation returns {@code true} if the request URI does not match
	 * "/signIn", indicating that the filter should not be applied to requests for
	 * the sign-in page.
	 * 
	 * @param request the HTTP request to check
	 * @return {@code true} if the filter should not be applied, {@code false}
	 *         otherwise
	 * @throws ServletException if an error occurs while processing the request
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		return !request.getServletPath().equals("/signIn");
	}

}

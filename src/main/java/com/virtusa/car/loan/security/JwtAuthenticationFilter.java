package com.virtusa.car.loan.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	static Logger lOGGER=LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//1. get token from request
		String requestToken=request.getHeader("Authorization");
		
		//The token is (Bearer ##########) 
		lOGGER.info(requestToken);
		
		//Now fetch two things from the token
			//1. username - with the help of JwtTokenHelper
			//2. actual token without Bearer
		String username=null;
		String actualToken=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			actualToken=requestToken.substring(7);
			
			try {
				username=this.jwtTokenHelper.getUsernameFromToken(actualToken);
			}
			catch(IllegalArgumentException e) {
				lOGGER.info("Unable to get JWT token");
			}
			catch(ExpiredJwtException e) {
				lOGGER.info("Jwt token has expired");
			}
			catch(MalformedJwtException e) {
				lOGGER.info("Invalid Jwt token");
			}
			
		}
		else {
			lOGGER.info("Jwt token is either null or does not begin with Bearer ");
		}
		
		//Once we extract the token, now we have to validate it
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(actualToken, userDetails)) {
				
				//token is valid
				//we need to do the authentication now
				//For this we need to create the object of UsernamePasswordAuthenticationToken
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				//We set the details in the usernamePasswordAuthenticationToken
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//Now we have to pass the object of authentication to SecurityContextHolder to set the authentication
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				//token is invalid
				lOGGER.info("Invalid Jwt token");
			}	
		}
		else {
			lOGGER.info("Username is null or security context is not null");
		}
		
		
		filterChain.doFilter(request, response);
	}


}

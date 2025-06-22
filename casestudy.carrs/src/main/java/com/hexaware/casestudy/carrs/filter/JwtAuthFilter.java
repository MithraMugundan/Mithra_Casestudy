package com.hexaware.casestudy.carrs.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hexaware.casestudy.carrs.config.UserInfoUserDetailsService;
import com.hexaware.casestudy.carrs.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
		
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserInfoUserDetailsService userDetailsService;
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			
			String path = request.getServletPath();
			if (path.equals("/api/authenticate/register") || path.equals("/api/authenticate/login")) {
			    filterChain.doFilter(request, response); // Let it go to controller
			    return;
			}
			
			  String authHeader = request.getHeader("Authorization");
		        String token = null;
		        String username = null;
		        if (authHeader != null && authHeader.startsWith("Bearer ")) {
		            token = authHeader.substring(7);
		            username = jwtService.extractUsername(token);
		            
		   
		        }
		        
		        
		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		        	
		        	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		            if (jwtService.validateToken(token,userDetails)) {
		               
		            	UsernamePasswordAuthenticationToken authToken =
		                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
		             
		            	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(authToken);
		            }
		        }
		        filterChain.doFilter(request, response);
		    
		
	}

}

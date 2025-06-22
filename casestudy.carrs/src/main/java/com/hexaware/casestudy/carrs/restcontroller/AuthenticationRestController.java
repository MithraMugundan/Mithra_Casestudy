/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: Manages authentication-related endpoints for both users and admins,
 * including login, logout, and verifying user credentials for secure access.
 */


package com.hexaware.casestudy.carrs.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.casestudy.carrs.dto.AuthRequest;
import com.hexaware.casestudy.carrs.dto.UserInfoDTO;
import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.exception.UserNameExistsException;
import com.hexaware.casestudy.carrs.service.IUserInfoService;
import com.hexaware.casestudy.carrs.service.JwtService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationRestController {

	@Autowired
	JwtService jwtService;
	
	@Autowired
	IUserInfoService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<UserInfoDTO> addUser(@RequestBody @Valid UserInfo userInfo) throws UserNameExistsException
	{
		return new ResponseEntity<UserInfoDTO>( userService.registerUser(userInfo), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		

		String token = null;

		if (authentication.isAuthenticated()) {

			// call generate token method from jwtService class

			token = jwtService.generateToken(authRequest.getUsername());

			//logger.info("Token : " + token);

		} else {

			//logger.info("invalid");

			throw new UsernameNotFoundException("UserName or Password in Invalid / Invalid Request");

		}
			return token;

	}
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
	    String username = request.get("username");
	    String newPassword = request.get("newPassword");

	    if (username == null || newPassword == null) {
	        return new ResponseEntity<>("Username or new password missing", HttpStatus.BAD_REQUEST);
	    }

	    boolean updated = userService.resetPassword(username, newPassword);
	    if (updated) {
	        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }
	}

}


/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: REST controller that manages user-related operations including
 * registration, login, fetching user details, and updating profiles.
 */

package com.hexaware.casestudy.carrs.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hexaware.casestudy.carrs.dto.UserInfoDTO;
import com.hexaware.casestudy.carrs.exception.UserNotFoundException;
import com.hexaware.casestudy.carrs.service.IUserInfoService;
import com.hexaware.casestudy.carrs.service.JwtService;



@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserInfoRestController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	IUserInfoService userService;
	
	
		// User-Based Operations
	
	 	@GetMapping("/getAllUsers")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
	        return new ResponseEntity<List<UserInfoDTO>>(userService.getAllUsers(), HttpStatus.OK);
	    }

	    @GetMapping("/getUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','customer')")
	    public ResponseEntity<UserInfoDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
	        return  new ResponseEntity<UserInfoDTO>(userService.getUserById(id), HttpStatus.OK);
	    }

	    @PutMapping("/updateUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','customer')")
	    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable Long id, @RequestBody UserInfoDTO user) throws UserNotFoundException {
	        return new ResponseEntity<UserInfoDTO>(userService.updateUser(id, user), HttpStatus.OK);
	    }

	    @DeleteMapping("/deactivateUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> deactivateUser(@PathVariable Long id) throws UserNotFoundException {
	        userService.deactivateUser(id);
	        return new ResponseEntity<String>("User deactivated successfully",HttpStatus.OK);
	    }
}
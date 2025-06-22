package com.hexaware.casestudy.carrs.service;

import java.util.List;

import com.hexaware.casestudy.carrs.dto.UserInfoDTO;
import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.exception.UserNameExistsException;
import com.hexaware.casestudy.carrs.exception.UserNotFoundException;



public interface IUserInfoService {
	
	public UserInfoDTO registerUser(UserInfo userInfo) throws UserNameExistsException;
	
	public List<UserInfoDTO> getAllUsers();
	
	public UserInfoDTO getUserById(Long id) throws UserNotFoundException;
	
	public UserInfoDTO updateUser(Long id, UserInfoDTO updatedUser) throws UserNotFoundException;
	
	public String deactivateUser(Long id) throws UserNotFoundException;
	
	public boolean resetPassword(String username, String newPassword);

	

}

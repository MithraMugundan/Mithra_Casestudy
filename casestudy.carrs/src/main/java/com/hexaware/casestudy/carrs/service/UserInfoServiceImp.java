
/**
 * Date: 01-06-2025
 * Author: Mithra M
 * Description: Implements user-related business logic including registration, login,
 * fetching user profiles, updating details, and role-based access management.
 */

package com.hexaware.casestudy.carrs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.dto.UserInfoDTO;
import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.exception.UserNameExistsException;
import com.hexaware.casestudy.carrs.exception.UserNotFoundException;
import com.hexaware.casestudy.carrs.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoServiceImp implements IUserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserInfoDTO registerUser(UserInfo userInfo) throws UserNameExistsException {
        if (userInfoRepository.findByUserName(userInfo.getUserName()) != null) {
            log.warn("Username already exists");
            throw new UserNameExistsException();
        }
        log.info("Registering new user with username={}, email={} and role={}", userInfo.getUserName(), userInfo.getEmail(), userInfo.getRoles());
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        UserInfo savedUser = userInfoRepository.save(userInfo);
        log.debug("User registered successfully: {}", savedUser);
        return EntityToDTO(userInfo);
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        log.info("Fetching all users");
        List<UserInfoDTO> usersDetails = new ArrayList<>();

        List<UserInfo> users = userInfoRepository.findAll();

        for (UserInfo userInfo : users) {
            usersDetails.add(EntityToDTO(userInfo));
        }
        log.debug("Total users fetched: {}", users.size());
        return usersDetails;
    }

    @Override
    public UserInfoDTO getUserById(Long id) throws UserNotFoundException {
        log.info("Fetching user with id={} ", id);
        UserInfo user = userInfoRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("User with id={} not found", id);
            throw new UserNotFoundException();
        }
        log.debug("User found: {}", user);
        return EntityToDTO(user);
    }

    @Override
    public UserInfoDTO updateUser(Long id, UserInfoDTO updatedUser) throws UserNotFoundException {
        log.info("Updating user with id={}", id);
        UserInfo existingUserInfo = userInfoRepository.findById(id).orElse(null);

        if (existingUserInfo == null) {
            log.warn("User with id={} not found for update", id);
            throw new UserNotFoundException();
        }
        existingUserInfo.setUserName(updatedUser.getUserName());
        existingUserInfo.setEmail(updatedUser.getEmail());
        existingUserInfo.setRoles(updatedUser.getRoles());
        existingUserInfo.setPhoneNumber(updatedUser.getPhoneNumber());

        log.debug("User with id={} updated successfully: {}", id, existingUserInfo);
        return EntityToDTO(userInfoRepository.save(existingUserInfo));
    }

    @Override
    public String deactivateUser(Long id) throws UserNotFoundException {
        log.info("Deactivating user with id={}", id);
        UserInfo user = userInfoRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("Attempted to deactivate non-existing user with id={}", id);
            throw new UserNotFoundException();
        }
        user.setRoles("INACTIVE");
        log.debug("User with id={} deactivated successfully", id);
        userInfoRepository.save(user);
        return "User deactivated!";
    }

    @Override
    public boolean resetPassword(String username, String newPassword) {
        log.info("Attempting password reset for username={}", username);
        Optional<UserInfo> userOpt = userInfoRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            UserInfo user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userInfoRepository.save(user);
            log.debug("Password reset successful for username={}", username);
            return true;
        }
        log.warn("Password reset failed. User not found: {}", username);
        return false;
    }

    private UserInfoDTO EntityToDTO(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userInfo.getUserId());
        userInfoDTO.setUserName(userInfo.getUserName());
        userInfoDTO.setEmail(userInfo.getEmail());
        userInfoDTO.setRoles(userInfo.getRoles());
        userInfoDTO.setPhoneNumber(userInfo.getPhoneNumber());
        return userInfoDTO;
    }
}

package com.hexaware.roadready.car.service;

import com.hexaware.roadready.car.dto.AuthResponseDTO;
import com.hexaware.roadready.car.dto.LoginRequestDTO;
import com.hexaware.roadready.car.dto.UsersDTO;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.exception.UserNotFoundException;
import com.hexaware.roadready.car.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Name        : Mithra
 * Date        : May 25, 2025 
 * Description : Implementation of user-related business logic such as user registration, authentication, and profile management.
 */

@Slf4j
@Service
public class UsersServiceImp implements IUsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Override
    public String registerUser(UsersDTO userDTO) {
        if (usersRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Users user = new Users();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());

        usersRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public AuthResponseDTO loginUser(LoginRequestDTO loginDTO) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginDTO.getEmail());
            return new AuthResponseDTO(token);
        } else {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    @Override
    public Users updateUser(int userId, UsersDTO userDTO) throws UserNotFoundException {
        log.info("Updating user with id: {}", userId);
        Optional<Users> optionalUser = usersRepository.findById((long) userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setRole(userDTO.getRole());
            return usersRepository.save(user);
        } else {
            log.warn("User with id {} not found for update", userId);
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public Users getUserById(int userId) throws UserNotFoundException {
        log.debug("Fetching user by id: {}", userId);
        return usersRepository.findById((long) userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new UserNotFoundException("User not found with id: " + userId);
                });
    }

    @Override
    public List<Users> getAllUsers() {
        log.info("Fetching all users");
        return usersRepository.findAll();
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        log.info("Deleting user with id: {}", userId);
        if (!usersRepository.existsById((long) userId)) {
            log.warn("User with id {} not found for delete", userId);
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        usersRepository.deleteById((long) userId);
    }

    @Override
    public Users findByEmail(String email) throws UserNotFoundException {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}

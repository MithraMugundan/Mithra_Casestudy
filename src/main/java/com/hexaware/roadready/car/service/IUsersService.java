package com.hexaware.roadready.car.service;

import java.util.List;

import com.hexaware.roadready.car.dto.AuthResponseDTO;
import com.hexaware.roadready.car.dto.LoginRequestDTO;
import com.hexaware.roadready.car.dto.UsersDTO;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.exception.UserNotFoundException;

import jakarta.validation.Valid;

public interface IUsersService {


    Users updateUser(int userId, UsersDTO userDTO) throws UserNotFoundException;

    Users getUserById(int userId) throws UserNotFoundException;

    List<Users> getAllUsers();

    void deleteUser(int userId) throws UserNotFoundException;

    Users findByEmail(String email) throws UserNotFoundException;

    String registerUser(@Valid UsersDTO userDTO);

    AuthResponseDTO loginUser(LoginRequestDTO loginDTO);
}

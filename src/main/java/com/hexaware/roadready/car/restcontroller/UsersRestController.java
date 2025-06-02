package com.hexaware.roadready.car.restcontroller;

import com.hexaware.roadready.car.dto.AuthResponseDTO;
import com.hexaware.roadready.car.dto.LoginRequestDTO;
import com.hexaware.roadready.car.dto.UsersDTO;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.service.IUsersService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    private IUsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UsersDTO userDTO) {
        return ResponseEntity.ok(usersService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(usersService.loginUser(loginDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId, Authentication authentication) {
        Users loggedInUser = usersService.findByEmail(authentication.getName());

        if (!loggedInUser.getRole().equals("ADMIN") && loggedInUser.getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Users user = usersService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<Users> updateUser(@PathVariable int userId, @RequestBody UsersDTO userDTO) {
        log.info("Updating user with id: {}", userId);
        Users updatedUser = usersService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<String> deleteUser(@PathVariable int userId, Authentication authentication) {
        Users loggedInUser = usersService.findByEmail(authentication.getName());

        if (loggedInUser.getRole().equals("ADMIN")) {
            usersService.deleteUser(userId);
            return ResponseEntity.ok("User deleted by admin");
        }

        if (loggedInUser.getRole().equals("USER")) {
            if (loggedInUser.getUserId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own account");
            }
            usersService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to delete users");
    }
}

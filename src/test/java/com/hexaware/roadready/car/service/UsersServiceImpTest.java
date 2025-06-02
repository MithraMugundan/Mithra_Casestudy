package com.hexaware.roadready.car.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.roadready.car.dto.AuthResponseDTO;
import com.hexaware.roadready.car.dto.LoginRequestDTO;
import com.hexaware.roadready.car.dto.UsersDTO;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.exception.UserNotFoundException;
import com.hexaware.roadready.car.repository.UsersRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

class UsersServiceImpTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UsersServiceImp usersServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UsersDTO dto = new UsersDTO();
        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("password");
        dto.setPhoneNumber("1234567890");
        dto.setAddress("123 Street");
        dto.setRole("USER");

        when(usersRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(usersRepository.save(any(Users.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = usersServiceImp.registerUser(dto);

        assertEquals("User registered successfully", result);
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        UsersDTO dto = new UsersDTO();
        dto.setEmail("test@example.com");

        when(usersRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usersServiceImp.registerUser(dto);
        });

        assertTrue(ex.getMessage().contains("Email already exists"));
    }

    @Test
    void testLoginUser_Success() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtService.generateToken(loginDTO.getEmail())).thenReturn("token123");

        AuthResponseDTO response = usersServiceImp.loginUser(loginDTO);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
    }

    @Test
    void testLoginUser_Failure() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrongpassword");

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(false);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usersServiceImp.loginUser(loginDTO);
        });

        assertTrue(ex.getMessage().contains("Invalid login credentials"));
    }

    @Test
    void testUpdateUser_Success() throws UserNotFoundException {
        int userId = 1;
        UsersDTO dto = new UsersDTO();
        dto.setFirstName("Updated");
        dto.setLastName("User");
        dto.setEmail("updated@example.com");
        dto.setPassword("newpass");
        dto.setPhoneNumber("9876543210");
        dto.setAddress("New Address");
        dto.setRole("ADMIN");

        Users existingUser = new Users();
        existingUser.setUserId((long) userId);

        when(usersRepository.findById((long) userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedNewPass");
        when(usersRepository.save(any(Users.class))).thenAnswer(i -> i.getArguments()[0]);

        Users updated = usersServiceImp.updateUser(userId, dto);

        assertEquals("Updated", updated.getFirstName());
        assertEquals("encodedNewPass", updated.getPassword());
    }

    @Test
    void testUpdateUser_NotFound() {
        int userId = 999;
        UsersDTO dto = new UsersDTO();

        when(usersRepository.findById((long) userId)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            usersServiceImp.updateUser(userId, dto);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void testGetUserById_Success() throws UserNotFoundException {
        int userId = 1;
        Users user = new Users();
        user.setUserId((long) userId);

        when(usersRepository.findById((long) userId)).thenReturn(Optional.of(user));

        Users found = usersServiceImp.getUserById(userId);

        assertNotNull(found);
        assertEquals(userId, found.getUserId());
    }

    @Test
    void testGetUserById_NotFound() {
        int userId = 999;

        when(usersRepository.findById((long) userId)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            usersServiceImp.getUserById(userId);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void testGetAllUsers() {
        Users u1 = new Users();
        u1.setUserId(1L);
        Users u2 = new Users();
        u2.setUserId(2L);

        when(usersRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Users> users = usersServiceImp.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser_Success() throws UserNotFoundException {
        int userId = 1;

        when(usersRepository.existsById((long) userId)).thenReturn(true);
        doNothing().when(usersRepository).deleteById((long) userId);

        assertDoesNotThrow(() -> usersServiceImp.deleteUser(userId));
        verify(usersRepository).deleteById((long) userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        int userId = 999;

        when(usersRepository.existsById((long) userId)).thenReturn(false);

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            usersServiceImp.deleteUser(userId);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void testFindByEmail_Success() throws UserNotFoundException {
        String email = "test@example.com";
        Users user = new Users();
        user.setEmail(email);

        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Users found = usersServiceImp.findByEmail(email);

        assertNotNull(found);
        assertEquals(email, found.getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        String email = "notfound@example.com";

        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            usersServiceImp.findByEmail(email);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }
}

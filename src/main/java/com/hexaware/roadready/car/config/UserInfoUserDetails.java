package com.hexaware.roadready.car.config;

import com.hexaware.roadready.car.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserInfoUserDetails implements UserDetails {

    private final Users user;

    public UserInfoUserDetails(Users user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getUserId(); // assuming userId is of type Long
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // assuming this is the password field
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // assuming this is the username field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

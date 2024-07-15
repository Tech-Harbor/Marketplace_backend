package com.example.backend.security.servers.details;

import com.example.backend.utils.enums.Role;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
public record MyUserDetails(UserSecurityDTO user) implements UserDetails {
    @Override
    public Collection<Role> getAuthorities() {
        return user.roles();
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.accountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.accountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.credentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.enabled();
    }
}
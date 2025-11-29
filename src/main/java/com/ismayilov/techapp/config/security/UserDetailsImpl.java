package com.ismayilov.techapp.config.security;

import com.ismayilov.techapp.entity.TechUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsImpl implements UserDetails {
    TechUser user;
    List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public UserDetailsImpl(TechUser user) {
        this.user = user;
        this.simpleGrantedAuthorities = Arrays
                .stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    public Long getId() {
        return user.getId();
    }

    public TechUser getTechUser() {
        return this.user;
    }

    @Override
    public String getUsername() {
        return user.getPin();
    }

    @Override
    public boolean isAccountNonExpired() {
        // user expired period
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // bloklanmis istifadeciler
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // password mueyyen muddet sonra deyisme serti
        return true;
    }

    @Override
    public boolean isEnabled() {
        // is user enabled ?
        return true;
    }
}

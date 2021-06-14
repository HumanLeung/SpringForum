package com.company.springforum.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JwtUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<? extends GrantedAuthority> grantedAuthorities;

    public JwtUserDetails() {

    }
    public JwtUserDetails(String email, String password, List<? extends GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtUserDetails that = (JwtUserDetails) o;
        return email.equals(that.email) && password.equals(that.password) && grantedAuthorities.equals(that.grantedAuthorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, grantedAuthorities);
    }
}

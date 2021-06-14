package com.company.springforum.security.jwt;

import com.company.springforum.repository.UserSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("JwtService")
public class JwtUserDetailsService implements UserDetailsService {

    private final UserSecurityRepo repo;

    @Autowired
    public JwtUserDetailsService(UserSecurityRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserSecurity user = repo.findUserSecurityByEmail(email);
        System.out.println(user);
        if (user == null){
            throw new UsernameNotFoundException("Not found: "+ email);
        }
        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
        for(RolesSecurity role: user.getRoles()){
            rolesList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return new JwtUserDetails(user.getEmail(), user.getPassword(), rolesList);
    }
}

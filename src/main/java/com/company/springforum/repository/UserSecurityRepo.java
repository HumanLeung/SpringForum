package com.company.springforum.repository;

import com.company.springforum.security.jwt.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSecurityRepo extends JpaRepository<UserSecurity,String> {
    List<UserSecurity> findUserByEmail(String name);
    UserSecurity findUserSecurityByEmail(String email);
    UserSecurity findByUserNum(String num);
}

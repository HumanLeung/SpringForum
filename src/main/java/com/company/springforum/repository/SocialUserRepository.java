package com.company.springforum.repository;

import com.company.springforum.model.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialLogin, String> {

     SocialLogin findBySocialId(String socialId);

}

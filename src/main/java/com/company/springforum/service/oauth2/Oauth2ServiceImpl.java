package com.company.springforum.service.oauth2;

import com.company.springforum.model.Roles;
import com.company.springforum.model.SocialLogin;
import com.company.springforum.model.User;
import com.company.springforum.repository.RoleRepository;
import com.company.springforum.repository.SocialUserRepository;
import com.company.springforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class Oauth2ServiceImpl implements Oauth2Service{

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public Oauth2ServiceImpl(UserRepository userRepository, SocialUserRepository socialUserRepository,
                             RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.socialUserRepository = socialUserRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public String addOauthUser(String username, String userNum, String socialId,String avatar, Integer userType) {
        SocialLogin socialLogin1 = new SocialLogin(socialId);
        User user = new User(username, userNum,avatar,userType);
        User user1  = userRepository.save(user);
        Roles role = new Roles("user");
        user1.addRoles(role);
        roleRepository.save(role);
        socialLogin1.setUser(user1);
        socialUserRepository.save(socialLogin1);
//        User user2 = userRepository.save(user1);
        return user1.getUserNum();
    }

    @Override
    public User getUser(String userName){
       return userRepository.findUserByUserNum(userName);
    }
}

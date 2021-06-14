package com.company.springforum.security.oauth2;



import com.company.springforum.model.SocialLogin;
import com.company.springforum.repository.SocialUserRepository;

//@Component
public class Oauth2VerifyService {

    private final SocialUserRepository repository;

    public Oauth2VerifyService(SocialUserRepository repository){
        this.repository = repository;
    }
    public SocialLogin loadUser(String id) {
      SocialLogin socialLogin = repository.findBySocialId(id);
      return null;
    }

}

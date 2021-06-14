package com.company.springforum.security.oauth2;


import com.company.springforum.model.SocialLogin;
import com.company.springforum.repository.SocialUserRepository;
import com.company.springforum.repository.UserRepository;
import com.company.springforum.service.oauth2.Oauth2Service;
import com.company.springforum.tools.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final SocialUserRepository repository;
    private final UserRepository userRepository;
    private final Oauth2Service oauth2Service;
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public Oauth2UserService(SocialUserRepository repository, Oauth2Service oauth2Service,
                             UserRepository userRepository, RedisTemplate<String, String> redisTemplate) {
        this.repository = repository;
        this.oauth2Service = oauth2Service;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return process(user);
    }
    public OAuth2User process(OAuth2User user) {
        String github = (String) user.getAttributes().get("node_id");
        System.out.println(github+ " socialLogin");
        if (github != null){
            GithubUserInfo socialUser = new GithubUserInfo(user.getAttributes());
            System.out.println("this is socialUser "+ socialUser);
            SocialLogin result = repository.findBySocialId(socialUser.getId());
            System.out.println("this is result "+result);
            if (result != null) {
              String userNum = result.getUser().getUserNum();
                String key = redisTemplate.opsForValue().get("oauthKey");
                System.out.println("this is restKey " + key);
                assert key != null;
                redisTemplate.opsForValue().set(key, userNum);
                return user;
            }else{
                int top = userRepository.max();
                String userNum = oauth2Service.addOauthUser(socialUser.getName(),
                        IdGenerator.generateId(top+1),
                        socialUser.getId(),socialUser.getAvatar(),2);
                String key;
                do {
                    key = redisTemplate.opsForValue().get("oauthKey");
                }while (key == null);
                   redisTemplate.opsForValue().set(key,userNum);
            }
        } else {
            GoogleUserInfo googleUserInfo = new GoogleUserInfo(user.getAttributes());
            SocialLogin result = repository.findBySocialId(googleUserInfo.getId());
            if (result != null) {
              String userNum = result.getUser().getUserNum();
                String key;
              do {
                  key = redisTemplate.opsForValue().get("oauthKey");
              } while (key == null);
                redisTemplate.opsForValue().set(key, userNum);
                return user;
            }else {
                int top = userRepository.max();
                String userNum = oauth2Service.addOauthUser(googleUserInfo.getName(),
                        IdGenerator.generateId(top+1),
                        googleUserInfo.getId(),googleUserInfo.getAvatar(),2);
                   String key;
                 do {
                   key = redisTemplate.opsForValue().get("oauthKey");
                } while (key == null);
                 redisTemplate.opsForValue().set(key, userNum);
            }
        }
        return user;
    }
}

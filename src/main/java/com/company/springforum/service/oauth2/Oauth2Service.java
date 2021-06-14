package com.company.springforum.service.oauth2;


import com.company.springforum.model.User;

public interface Oauth2Service {
    public String addOauthUser(String username, String userNum, String socialId,String avatar, Integer userType);

    public User getUser(String userName);
}

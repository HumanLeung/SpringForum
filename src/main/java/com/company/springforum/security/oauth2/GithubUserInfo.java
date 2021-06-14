package com.company.springforum.security.oauth2;


import java.util.Map;

public class GithubUserInfo{

    private final Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
       Integer id = (Integer) attributes.get("id");
        return id+"" ;
    }
    public String getName() {
        return (String) attributes.get("login");
    }

    public String getAvatar() {
        return (String) attributes.get("avatar_url");
    }

}

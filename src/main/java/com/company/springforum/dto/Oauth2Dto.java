package com.company.springforum.dto;

import com.company.springforum.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Oauth2Dto {
    private User user;
    private String Oid;
}

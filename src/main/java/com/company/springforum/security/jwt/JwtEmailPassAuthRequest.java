package com.company.springforum.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtEmailPassAuthRequest {
    private String email;
    private String password;
}

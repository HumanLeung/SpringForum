package com.company.springforum.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VerifyCode {
    public String code;
    public String key;
}

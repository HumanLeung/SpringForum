package com.company.springforum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class RegisterStatus {
    private String state;
    private String message;
}

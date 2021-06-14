package com.company.springforum.pojo;


import com.company.springforum.dto.PasswordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhoneStatus {
    private String state;
    private String jwt;
}

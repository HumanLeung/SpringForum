package com.company.springforum.controller;

import com.company.springforum.dto.LoginSuccess;
import com.company.springforum.dto.TokenEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redirect")
public class RedirectController {
    @GetMapping("/loginSuccess/{data}")
    public ResponseEntity<LoginSuccess> loginSuccess(@PathVariable String data) {
        System.out.println(data);
        return new ResponseEntity<>(new LoginSuccess("OK",new TokenEntity(data)), HttpStatus.OK);
    }

}

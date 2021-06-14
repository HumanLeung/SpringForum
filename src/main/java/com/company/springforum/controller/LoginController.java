package com.company.springforum.controller;



import com.company.springforum.dto.PasswordDto;
import com.company.springforum.pojo.*;
import com.company.springforum.service.register.RegisterService;
import com.company.springforum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@CrossOrigin(origins = "*",  allowedHeaders = "*")
@RestController
@RequestMapping("/api/entry")
public class LoginController {

    public RegisterService registerService;
    public UserService userService;

    @Autowired
    public LoginController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterStatus> registerByEmail(@RequestBody Receiver receiver) throws UnsupportedEncodingException, MessagingException {

    int state = registerService.registerByEmail(receiver);
    if (state == 1){
        return new ResponseEntity<>(new RegisterStatus("OK", receiver.getEmailAddress()), HttpStatus.OK);
    }
    return new ResponseEntity<>(new RegisterStatus("Bad","failed"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<Status> verifyCode(@RequestBody VerifyCode code) {
        Receiver receiver  = registerService.verifyCode(code.getCode(),code.getKey());
        if (receiver != null) {
            return new ResponseEntity<>(new Status("OK", receiver), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Status("Bad",null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public ResponseEntity<Status> test() {
            return new ResponseEntity<>(new Status("OK",  new Receiver("helol","askdje","asdad")), HttpStatus.OK);
    }

    @PostMapping("/phonelogin")
    public ResponseEntity<RegisterStatus> loginByPhone(@RequestBody Phone phone) {
        int state = registerService.loginBySms(phone);
        if (state == 1) {
            return new ResponseEntity<>(new RegisterStatus("OK", phone.getPhone()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RegisterStatus("Bad","failed"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verifynum")
    public ResponseEntity<PhoneStatus> verifyNum(@RequestBody VerifyCode code) {
        String jwt = registerService.verifyDigit(code.getCode(),code.getKey());
        if (jwt != null) {
            return new ResponseEntity<>(new PhoneStatus("OK", jwt), HttpStatus.OK);
        }
        return new ResponseEntity<>(new PhoneStatus("Bad",null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/freelogin")
    public String getEmail() {
        return "email";
    }

}
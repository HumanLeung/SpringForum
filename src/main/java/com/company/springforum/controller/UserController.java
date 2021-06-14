package com.company.springforum.controller;


import com.company.springforum.dto.ProfileDTO;
import com.company.springforum.dto.StatusDTO;
import com.company.springforum.dto.UserDto;
import com.company.springforum.model.User;
import com.company.springforum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/load/{email}")
    public ResponseEntity<UserDto> loadUser(@PathVariable String email) {
            User user =  service.loadUser(email);
            if (user != null) {
                return new ResponseEntity<>(new UserDto("OK", user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserDto("FAIL",null), HttpStatus.BAD_REQUEST);
            }
    }

    @PutMapping("/update/{num}")
    public ResponseEntity<StatusDTO> updateUser(@PathVariable String num, @RequestBody ProfileDTO dto) {
       String state = service.updateByNum(num,dto.getEmail(),dto.getUsername(),dto.getPhoneNum());
       if (state.equals("success")) {
       return new ResponseEntity<>(new StatusDTO("OK"),HttpStatus.OK);
       } else {
           return new ResponseEntity<>(new StatusDTO(state),HttpStatus.BAD_REQUEST);
       }
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("num") String num) {
        service.uploadAvatar(file,num);
    }

//    @PostMapping("/mark/")
}

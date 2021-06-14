package com.company.springforum.service.user;

import com.company.springforum.model.User;
import com.company.springforum.pojo.Phone;
import com.company.springforum.pojo.Receiver;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public void addUser(User user);

    public User loadUser(String email);

    public boolean registerUser(Receiver receiver);

    public boolean AddUserByPhone(Phone phone);

    public boolean VerifyAccount(Receiver receiver);

    public boolean deleteUserByEmail(String email);

    public String updateByNum(String num, String email, String username, String phoneNum);

    public void uploadAvatar(MultipartFile file, String num);

}

package com.company.springforum.service.user;

import com.company.springforum.model.Roles;
import com.company.springforum.model.User;
import com.company.springforum.pojo.Phone;
import com.company.springforum.pojo.Receiver;
import com.company.springforum.repository.RoleRepository;
import com.company.springforum.repository.RoomRepository;
import com.company.springforum.repository.UserRepository;
import com.company.springforum.tools.FileTool;
import com.company.springforum.tools.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServiceImpl implements UserService{

    public UserRepository userRepository;
    public RoleRepository roleRepository;
    public PasswordEncoder passwordEncoder;
    public RoomRepository roomRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           RoomRepository roomRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roomRepository = roomRepository;
    }

    @Override
    public void addUser(User user) {
       userRepository.save(user);
    }

    @Override
    @Transactional
    public User loadUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean registerUser(Receiver receiver) {
        if (userRepository.findUsersByEmail(receiver.getEmailAddress()).size() == 0) {
            int top = userRepository.max();
            User user = userRepository.save(new User(receiver.getUsername(), receiver.getEmailAddress(),
                    passwordEncoder.encode(receiver.getPassword()), IdGenerator.generateId(top + 1),1));
            Roles role = new Roles("user");
            user.addRoles(role);
            Roles body = roleRepository.save(role);
            return body != null;
        }
        return false;
    }

    @Override
    public boolean AddUserByPhone(Phone phone) {
        if(userRepository.findUsersByPhoneNum(phone.getPhone()).size() == 0){
            int top = userRepository.max();
            User user = userRepository.save(new User(phone.getPhone(),null,null,IdGenerator.generateId(top+1),1));
            Roles role = new Roles("user");
            user.addRoles(role);
            Roles body = roleRepository.save(role);
            return body != null;
        }
        return false;
    }

    @Override
    public boolean VerifyAccount(Receiver receiver) {
         User user = userRepository.findUserByEmail(receiver.getEmailAddress());
         return user == null;
    }

    @Transactional
    @Override
    public boolean deleteUserByEmail(String email) {
        Integer num = userRepository.deleteUserByEmail(email);
        return num != 0;
    }

    @Override
    public String updateByNum(String num, String email, String username, String phoneNum) {
       User user = userRepository.findUserByUserNum(num);
       if (email.length()>5) {
           User e = userRepository.findUserByEmail(email);
           if (e == null || e.getUserNum().equals(num)) {
               user.setEmail(email);
           } else {
               return "email";
           }
       }
       if (phoneNum.length()>5) {
           System.out.println("this is phone "+ phoneNum);
           User p = userRepository.findUserByPhoneNum(phoneNum);
           System.out.println(p);
           if (p == null || p.getUserNum().equals(num)) {
               user.setPhoneNum(phoneNum);
           } else {
               return "phone";
           }
       }
        if (username.length()>1) {
            user.setUsername(username);
        }
        User result =  userRepository.save(user);
        return "success";

    }

    @Override
    public void uploadAvatar(MultipartFile file, String num) {
        String Paths = "src/main/resources/static/myforum/"+num+"/avatar";
        String path = FileTool.DirectoryTool(Paths);
        FileTool.createFile(path,file);

        User user = userRepository.findUserByUserNum(num);
        user.setAvatar("http://localhost:8080/myforum/"+num+"/avatar/"+file.getOriginalFilename());
        userRepository.save(user);
        System.out.println(file);
    }
}

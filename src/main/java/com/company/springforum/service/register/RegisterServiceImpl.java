package com.company.springforum.service.register;



import com.company.springforum.model.User;
import com.company.springforum.pojo.Phone;
import com.company.springforum.pojo.Receiver;
import com.company.springforum.repository.RoleRepository;
import com.company.springforum.repository.UserRepository;
import com.company.springforum.repository.UserSecurityRepo;
import com.company.springforum.security.jwt.JwtUserDetailsService;
import com.company.springforum.security.jwtutil.JwtUtil;
import com.company.springforum.service.email.EmailService;
import com.company.springforum.service.email.Template;
import com.company.springforum.service.email.VerifyCode;
import com.company.springforum.service.oauth2.OauthStorage;
import com.company.springforum.service.sms.CodeGenerator;
import com.company.springforum.service.sms.SmsService;
import com.company.springforum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

@Service
public class RegisterServiceImpl implements RegisterService{

    public EmailService emailService;
    public VerifyCode code;
    public Template template;
    public TempStorage tempStorage;
    public SmsService service;
    public CodeGenerator generator;
    public UserRepository userRepository;
    public RoleRepository roleRepository;
    public PasswordEncoder passwordEncoder;
    public PhoneStorage phoneStorage;
    public UserService userService;
    public OauthStorage oauthStorage;
    public UserSecurityRepo userSecurityRepo;
    public JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public RegisterServiceImpl(EmailService emailService, VerifyCode code, Template template,
                               TempStorage tempStorage, CodeGenerator generator, SmsService service,
                               UserRepository userRepository, PasswordEncoder passwordEncoder,
                               RoleRepository roleRepository, PhoneStorage phoneStorage, UserService userService,
                               OauthStorage oauthStorage, UserSecurityRepo userSecurityRepo,
                               JwtUserDetailsService jwtUserDetailsService) {
        this.emailService = emailService;
        this.code = code;
        this.template = template;
        this.tempStorage = tempStorage;
        this.generator = generator;
        this.service = service;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.phoneStorage = phoneStorage;
        this.userService = userService;
        this.oauthStorage = oauthStorage;
        this.userSecurityRepo = userSecurityRepo;
        this.jwtUserDetailsService = jwtUserDetailsService;
        System.out.println(code);
    }
    @Override
    @Transactional
    public int registerByEmail(Receiver receiver) {
        try {
            if (userService.VerifyAccount(receiver)){
                int newCode = code.generateCode(receiver.getEmailAddress());
                System.out.println(newCode);
                tempStorage.saveObject(receiver);
                emailService.sendEmail(receiver,newCode,template);
                return 1;
            }
        } catch (MessagingException | UnsupportedEncodingException ignored) {
            throw new RuntimeException("something wrong");
        }
        return -1;
    }

    @Transactional
    @Override
    public Receiver verifyCode(String charCode, String key){
         Receiver receiver = null;
         int verifyCode = Integer.parseInt(charCode);
        int receivedCode = code.getCode(key);
        System.out.println(key);
        System.out.println(verifyCode);
        System.out.println(receivedCode);
        if (receivedCode > 0) {
           if (verifyCode == receivedCode) {
               try {
                   receiver = tempStorage.getObject(key);
                   boolean result = userService.registerUser(receiver);
                   if (!result) {
                       code.clearOTP(key);
                       tempStorage.clearObject(key);
                       return null;
                   }
               } catch (ExecutionException e) {
                   e.printStackTrace();
               }
               code.clearOTP(key);
               tempStorage.clearObject(key);
               return receiver;
           }
        }
       return null;
    }

    public boolean checkIfExists(String phone) {
      User user = userRepository.findUserByPhoneNum(phone);
        return user != null;
    }

    @Transactional
    @Override
    public int loginBySms(Phone phone) {
        int digit = 0;
        if (checkIfExists(phone.getPhone())){
                digit = generator.generateCode(phone.getPhone());
                phoneStorage.saveObject(phone);
                System.out.println(digit + " this is digit");
                service.sendSms(phone, digit);
                return 1 ;
        }
        return -1;
    }

    @Transactional
    @Override
    public String verifyDigit(String charCode, String key){
        Phone phone = null;
        int verifyCode = Integer.parseInt(charCode);
        int receivedCode = generator.getCode(key);
        User user = null;
        System.out.println(key);
        System.out.println(verifyCode);
        System.out.println(receivedCode);
        if (receivedCode > 0) {
            if (verifyCode == receivedCode) {
                try {
                    phone = phoneStorage.getObject(key);
                    user = userRepository.findUserByPhoneNum(phone.getPhone());
                    if (user != null){
                        generator.clearCode(key);
                        phoneStorage.clearObject(key);
                        UserDetails jwtUserDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());
                       String jwt = JwtUtil.generateToken(jwtUserDetails);
                       return "Bearer " + jwt;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        return null;
    }
}

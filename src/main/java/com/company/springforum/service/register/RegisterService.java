package com.company.springforum.service.register;


import com.company.springforum.dto.PasswordDto;
import com.company.springforum.pojo.Phone;
import com.company.springforum.pojo.Receiver;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface RegisterService {

    public int registerByEmail(Receiver receiver) throws UnsupportedEncodingException, MessagingException;

    public Receiver verifyCode(String charCode, String key);

    public int loginBySms(Phone phone);

    public String verifyDigit(String charCode, String key);

}

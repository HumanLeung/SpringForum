package com.company.springforum.service.sms;

import com.company.springforum.pojo.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final SmsSender smsSender;
    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender){
        this.smsSender = smsSender;
    }

    public void sendSms(Phone phone, Integer digit) {
        smsSender.sendSms(phone,digit);
    }
}

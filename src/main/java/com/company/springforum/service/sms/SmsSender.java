package com.company.springforum.service.sms;


import com.company.springforum.pojo.Phone;

public interface SmsSender {
    void sendSms(Phone phone, Integer digit);
}

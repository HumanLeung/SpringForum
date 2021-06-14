package com.company.springforum.controller;


import com.company.springforum.service.sms.SmsService;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@RequestMapping("api/sms")
public class SmsController {

    private final SmsService service;

    public SmsController(SmsService service) {
        this.service = service;
    }
//    @PostMapping
//    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
//        service.sendSms(smsRequest);
//    }

}

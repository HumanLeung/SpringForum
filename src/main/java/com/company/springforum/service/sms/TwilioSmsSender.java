package com.company.springforum.service.sms;

import com.company.springforum.config.TwilioConfiguration;
import com.company.springforum.pojo.Phone;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("twilio")
public class TwilioSmsSender implements SmsSender{

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }
    @Override
    public void sendSms(Phone phone, Integer digit) {
      if (isPhoneNumberValid(phone.getPhone())){
          PhoneNumber to = new PhoneNumber(phone.getPhone());
          PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
          String message = "Welcome to MyForum! This is your code: " +
                  digit + " never share your code with anyone else. ";
          MessageCreator creator = Message.creator(to, from, message);
          creator.create();
          LOGGER.info("Send sms {}", phone);
      } else {
          throw new IllegalArgumentException(
                  "Phone number [" + phone.getPhone() + "] is not a valid number"
          );
      }
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        System.out.println(phoneNumber);
        return true;
    }
}

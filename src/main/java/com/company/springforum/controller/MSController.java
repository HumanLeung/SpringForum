package com.company.springforum.controller;

import com.company.springforum.pojo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MSController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/chat/{num}")
//    @SendTo("/topic/public")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String num) {
        System.out.println("num "+num);
      template.convertAndSend("/topic/public/" + num, chatMessage);
    }
}

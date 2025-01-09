package org.example.messagehibernate.Controller;

import org.example.messagehibernate.Controller.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSCONTROLLER {

    @MessageMapping("/hello")
    @SendTo("/topic/public")
    public Message greeting(@Payload Message message) throws Exception {
        Thread.sleep(1000);
        return new Message("Server", "Hello, " + message.getSender());
    }
}

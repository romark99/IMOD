package com.brest.bstu.po1.imod.web_app;

import com.brest.bstu.po1.imod.model.Greeting;
import com.brest.bstu.po1.imod.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Welcome, " + HtmlUtils.htmlEscape(message.getName()) + " " + HtmlUtils.htmlEscape(message.getSurname()) + "!");
    }

}
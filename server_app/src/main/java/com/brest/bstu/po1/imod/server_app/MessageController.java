package com.brest.bstu.po1.imod.server_app;

import com.brest.bstu.po1.imod.model.SimpleMessage;
import com.brest.bstu.po1.imod.model.SimpleResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public SimpleResponse greeting(SimpleMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        String response = "Hello, " + HtmlUtils.htmlEscape(message.getName()) + " " + HtmlUtils.htmlEscape(message.getSurname());
        return new SimpleResponse(response);
    }

}
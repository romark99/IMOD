package com.brest.bstu.po1.imod.web_app;

import com.brest.bstu.po1.imod.model.Greeting;
import com.brest.bstu.po1.imod.model.InputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(InputMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getTime()) + " " +
                            HtmlUtils.htmlEscape(message.getNickname()) + " wrote: '" +
                            HtmlUtils.htmlEscape(message.getMessage()) + "'");
    }

    @MessageMapping("/showHistory")
    @SendTo("/topic/showHistory")
    public Greeting showHistory(InputMessage message) {
        return new Greeting(HtmlUtils.htmlEscape(message.getNickname()) + " wrote: '" +
                           HtmlUtils.htmlEscape(message.getMessage()) + "'");
    }

    @MessageMapping("/removeHistory")
    @SendTo("/topic/removeHistory")
    public Greeting removeHistory(InputMessage message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getNickname()) + " wrote: '" +
                            HtmlUtils.htmlEscape(message.getMessage()) + "'");
    }

}
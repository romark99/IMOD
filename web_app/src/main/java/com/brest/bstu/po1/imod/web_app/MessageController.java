package com.brest.bstu.po1.imod.web_app;

import com.brest.bstu.po1.imod.dao.GreetingDao;
import com.brest.bstu.po1.imod.model.Greeting;
import com.brest.bstu.po1.imod.model.InputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @Autowired
    private GreetingDao greetingDao;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(InputMessage message) throws Exception {
        Greeting greeting = new Greeting(HtmlUtils.htmlEscape(message.getTime()) + " " +
                                         HtmlUtils.htmlEscape(message.getNickname()) + " wrote: '" +
                                         HtmlUtils.htmlEscape(message.getMessage()) + "'");
        Greeting newGreeting = greetingDao.addGreeting(greeting);
        Thread.sleep(500); // simulated delay
        return newGreeting;
    }

    @MessageMapping("/showHistory")
    @SendTo("/topic/showHistory")
    public Greeting[] showHistory() {
        return greetingDao.getAllGreetings();
    }

    @MessageMapping("/removeHistory")
    @SendTo("/topic/removeHistory")
    public Greeting removeHistory(InputMessage message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getNickname()) + " wrote: '" +
                            HtmlUtils.htmlEscape(message.getMessage()) + "'");
    }

}
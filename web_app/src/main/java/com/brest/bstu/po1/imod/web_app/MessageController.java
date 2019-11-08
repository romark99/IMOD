package com.brest.bstu.po1.imod.web_app;

import com.brest.bstu.po1.imod.dao.GreetingDao;
import com.brest.bstu.po1.imod.model.Greeting;
import com.brest.bstu.po1.imod.model.GreetingsAndRoom;
import com.brest.bstu.po1.imod.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private GreetingDao greetingDao;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Greeting message) throws Exception {
        LOGGER.debug("greeting({})", message);
        Greeting newGreeting = greetingDao.addGreeting(message);
        Thread.sleep(500); // simulated delay
        LOGGER.debug("greeting() returned {}", newGreeting);
        return newGreeting;
    }

    @MessageMapping("/showHistory")
    @SendTo("/topic/showHistory")
    public GreetingsAndRoom showHistory(Integer room) {
        LOGGER.debug("showHistory({})", room);
        Greeting[] greetings = greetingDao.getAllGreetingsByRoom(room);
        GreetingsAndRoom greetingsAndRoom = new GreetingsAndRoom();
        greetingsAndRoom.setGreetings(greetings);
        greetingsAndRoom.setRoom(room);
        LOGGER.debug("showHistory() returned {}", Arrays.toString(greetings));
        return greetingsAndRoom;
    }

    @MessageMapping("/removeHistory")
    @SendTo("/topic/removeHistory")
    public Integer removeHistory(Integer room) {
        LOGGER.debug("removeHistory({})", room);
        greetingDao.removeAllGreetingsByRoom(room);
        LOGGER.debug("removeHistory() return {}", room);
        return room;
    }

    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public User triggerTyping(User user) {
        LOGGER.debug("triggerTyping({})", user);
        LOGGER.debug("triggerTyping() returned {}", user);
        return user;
    }
}
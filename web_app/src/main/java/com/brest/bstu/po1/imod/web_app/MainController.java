package com.brest.bstu.po1.imod.web_app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping(value = "/")
    public String mainPage() {
        LOGGER.debug("mainPage()");
        LOGGER.debug("mainPage() returned index");
        return "index";
    }

}
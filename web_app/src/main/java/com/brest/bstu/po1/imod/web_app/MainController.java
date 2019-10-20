package com.brest.bstu.po1.imod.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String mainPage() {
        return "index";
    }

}
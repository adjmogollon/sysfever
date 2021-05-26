package com.sysfever.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    @RequestMapping({"/","/index"})
    public String index(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        return "index";
    }
    
}

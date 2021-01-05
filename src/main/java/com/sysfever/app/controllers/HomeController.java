package com.sysfever.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    @RequestMapping({"/","/index"})
    public String index(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        return "index";
    }
    
}

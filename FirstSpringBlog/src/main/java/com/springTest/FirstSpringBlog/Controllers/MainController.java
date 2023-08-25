package com.springTest.FirstSpringBlog.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(@RequestParam(name = "login", required = false, defaultValue = "userLogin") String login,
                       @RequestParam(name = "pass", required = false, defaultValue = "userPass") String pass,
                       Model model){
        model.addAttribute("login", login);
        model.addAttribute("pass", pass);

        return "main";
    }
    @GetMapping("/about")
    public String about(Model model){

        return "about";
    }
    @GetMapping("/contacts")
    public String contacts(Model model){

        return "contacts";
    }
}

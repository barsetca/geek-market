package com.cherniak.geek.market.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping
    public String goRoot() {
        return "redirect:/profile";
    }
}

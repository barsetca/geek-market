package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class UserController {

    UserRepository userService;

    @GetMapping("/users")
    public String getAll(Model model) {
        List<User> users = userService.findAll();
        users.forEach(System.out::println);
        model.addAttribute("users", users);
        return "users";
    }
}

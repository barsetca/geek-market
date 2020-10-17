package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Role;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.RoleService;
import com.cherniak.geek.market.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    UserService userService;

    RoleService roleService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String profilePage() {
        return "profile";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute  @Validated User user) throws RoleNotFoundException {
        System.out.println(user);
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName("ROLE_USER").orElseThrow(() ->
                new RoleNotFoundException("ROLE_USER not found"));
        roles.add(role);
        user.setRoles(roles);
        User newUser = userService.save(user);
        System.out.println(newUser);

        return "redirect:/products";

    }
}

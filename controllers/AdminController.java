package com.example.spring_test.controllers;

import com.example.spring_test.models.User;
import com.example.spring_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users",  users);
        return "admin";
    }
    @GetMapping("/delete-user/{id}")
    public String delUser(@PathVariable Long id) {
        userService.deleteUsrById(id);
        return "redirect:/admin";
    }
}

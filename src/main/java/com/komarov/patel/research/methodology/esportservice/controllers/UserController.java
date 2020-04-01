package com.komarov.patel.research.methodology.esportservice.controllers;

import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.service.SecurityService;
import com.komarov.patel.research.methodology.esportservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user, BindingResult result) {

        User existing = userService.findByUsername(user.getUsername());
        if(existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if(!user.getPassword().equals(user.getPasswordConfirm())) {
            result.rejectValue("password", null, "There password and confirmation DO NOT MATCH!");
        }
        if (result.hasErrors()){
            return "registration";
        }
        userService.save(user);

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/welcome")
    @ResponseBody
    public String index() {
        return "Welcome, authorized user!";
    }
}

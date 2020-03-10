package com.komarov.patel.research.methodology.esportservice.controllers;

import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.service.SecurityService;
import com.komarov.patel.research.methodology.esportservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User userForm) {
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "ok";
    }

    @RequestMapping("/welcome")
    public String index() {
        return "Welcome, authorized user!";
    }
}

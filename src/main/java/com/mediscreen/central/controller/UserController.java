package com.mediscreen.central.controller;

import com.mediscreen.central.Model.User;
import com.mediscreen.central.proxy.UserClientProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserClientProxy userClientProxy;

    public UserController(UserClientProxy userClientProxy) {
        this.userClientProxy = userClientProxy;
    }


    @GetMapping ("/getUser")
    public User getUser(@RequestParam String username, String password) {
        return userClientProxy.getUserByUsernameAndPassword(username, password);
    }




}

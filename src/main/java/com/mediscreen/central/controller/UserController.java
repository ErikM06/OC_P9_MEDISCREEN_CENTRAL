package com.mediscreen.central.controller;


import com.mediscreen.central.Model.User;
import com.mediscreen.central.proxy.UserClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserClientProxy userClientProxy;

    public UserController(UserClientProxy userClientProxy) {
        this.userClientProxy = userClientProxy;
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String username) {
        logger.info("in /getUser");
        return userClientProxy.getUserByUsernameAndPassword(username);
    }




}

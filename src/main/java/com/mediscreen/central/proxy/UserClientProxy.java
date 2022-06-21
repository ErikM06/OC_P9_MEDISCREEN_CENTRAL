package com.mediscreen.central.proxy;

import com.mediscreen.central.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mediscreen-user")
public interface UserClientProxy {

    @GetMapping (value = "/getUser")
    User getUserByUsernameAndPassword(@RequestParam String username, String password);
}

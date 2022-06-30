package com.mediscreen.central.service;

import com.mediscreen.central.Model.User;
import com.mediscreen.central.proxy.UserClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserClientProxy userClientProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userClientProxy.getUserByUsernameAndPassword(username);
        logger.info("user is : "+user.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException(username + "/s not found");

        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        logger.info("loading User by username: " + username + " and have role : " + authorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }




}

package com.mediscreen.central.service;

/* @Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserClientProxy userClientProxy;

    public UserDetailsServiceImpl(UserClientProxy userClientProxy) {
        this.userClientProxy = userClientProxy;
    }

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




} */

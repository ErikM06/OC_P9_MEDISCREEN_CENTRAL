package com.mediscreen.central.configuration;

/* @Configuration
@EnableWebSecurity
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize
                        .antMatchers("/resources/**", "/login").permitAll()
                        .antMatchers("/central/**").authenticated()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and().exceptionHandling()
                        .and()
                        .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/central/getPatientList")
                        .and()
                        .logout().logoutSuccessUrl("/logout");
                http.csrf().disable();
            } catch (Exception e) {
                logger.info("in exception customSecurityFilter "+e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



} */

package com.mediscreen.central.configuration;

/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                        .antMatchers("/resources/**", "/login").permitAll()
                        .antMatchers("/central/**").authenticated()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                        .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/central/getPatientList")
                        .and()
                        .logout().logoutSuccessUrl("/logout");
                http.csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



} */

package com.luv2Code.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails demon = User.builder()
                .username("demon")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails clay = User.builder()
                .username("clay")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails curry = User.builder()
                .username("curry")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();
        //Returning the Instance
        return new InMemoryUserDetailsManager(demon, clay, curry);
    }

    //Custom Login Form in the Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .anyRequest().authenticated()  //Means the User Should be Logged in
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")//Need a Controller Method To handle this Request
                                .loginProcessingUrl("/authenticateTheUser")//Login Form should POST data to this URL for processing
                                .permitAll()//Allow everyone to the see the Login Page
                )
                .logout(logout -> logout.permitAll()
                );


        return http.build();
    }
}













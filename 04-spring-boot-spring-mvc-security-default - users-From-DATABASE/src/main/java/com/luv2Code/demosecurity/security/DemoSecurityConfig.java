package com.luv2Code.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    //Adding the Support for the JDBC and No more hardcoding the Users
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        return new JdbcUserDetailsManager(dataSource);
    }//This tells the Spring security to use  JDBC authentication with the data Source.

      //Custom Login Form in the Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("EMPLOYEE")
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated()  //Means the User Should be Logged in
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")//Need a Controller Method To handle this Request
                                .loginProcessingUrl("/authenticateTheUser")//Login Form should POST data to this URL for processing
                                .permitAll()//Allow everyone to the see the Login Page
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                                configurer.accessDeniedPage("/access-denied")
                );


        return http.build();
    }
}

//Hardcoded values of the Users
  /*  @Bean
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
    }*/











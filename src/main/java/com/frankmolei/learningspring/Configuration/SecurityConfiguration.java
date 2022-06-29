package com.frankmolei.learningspring.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration{

    @Bean
    public SecurityFilterChain configuration(HttpSecurity http) throws  Exception{
        http.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")  //ESpecificamos que el get es para cualquiera
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails andres = User.builder().username("andres").password(passwordEncoder().encode("password"))
                .roles("USER").build();

        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("password"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(andres,admin);
    }

}

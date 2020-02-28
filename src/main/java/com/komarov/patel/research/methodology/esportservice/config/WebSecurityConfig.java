package com.komarov.patel.research.methodology.esportservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password").permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("bkomarov@example.com")
                .password(bCryptPasswordEncoder.encode("qwerty"))// Spring Security 5 requires specifying the password storage format
                .roles("USER");
        auth.inMemoryAuthentication()
                .withUser("bestjohn@example.com")
                .password(bCryptPasswordEncoder.encode("asdfgh"))// Spring Security 5 requires specifying the password storage format
                .roles("USER");
        auth.inMemoryAuthentication()
                .withUser("rick@example.com")
                .password(bCryptPasswordEncoder.encode("hgfdsa"))// Spring Security 5 requires specifying the password storage format
                .roles("USER");
    }
}
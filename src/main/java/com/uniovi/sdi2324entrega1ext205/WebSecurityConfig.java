package com.uniovi.sdi2324entrega1ext205;


import com.uniovi.sdi2324entrega1ext205.customHandlers.CustomAuthenFailureHandler;
import com.uniovi.sdi2324entrega1ext205.customHandlers.CustomAuthenSuccesHandler;
import com.uniovi.sdi2324entrega1ext205.customHandlers.CustomLogoutHandler;
import com.uniovi.sdi2324entrega1ext205.customHandlers.CustomUserProfileAccesVoter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenFailureHandler customAuthenFailureHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomAuthenSuccesHandler customAuthenSuccesHandler;


    private final CustomUserProfileAccesVoter customUserProfileAccesVoter;

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    private final AuthenticationConfiguration authenticationConfiguration;

    public WebSecurityConfig(CustomAuthenFailureHandler customAuthenFailureHandler, CustomLogoutHandler customLogoutHandler
            , CustomAuthenSuccesHandler customAuthenSuccesHandler, CustomUserProfileAccesVoter customUserProfileAccesVoter, AuthenticationConfiguration authenticationConfiguration) {
        this.customAuthenFailureHandler = customAuthenFailureHandler;
        this.customLogoutHandler = customLogoutHandler;
        this.customAuthenSuccesHandler = customAuthenSuccesHandler;
        this.customUserProfileAccesVoter = customUserProfileAccesVoter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(customUserProfileAccesVoter);
        return new UnanimousBased(decisionVoters);
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/post/**").authenticated()
                .antMatchers("/user/*").authenticated()//.accessDecisionManager(accessDecisionManager())
               //.antMatchers("/user/*").authenticated().
                    .anyRequest().authenticated()
                .and()
                .formLogin().failureHandler(customAuthenFailureHandler).successHandler(customAuthenSuccesHandler)
                .loginPage("/login")
//                .defaultSuccessUrl("/user/list")
                .permitAll()
                .and()
                .logout().logoutSuccessHandler(customLogoutHandler)
                .permitAll()
        ;
    }

}


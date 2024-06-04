package com.uniovi.sdi2324entrega1ext205.customHandlers;


import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.services.LoggerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final LoggerService loggerService;

    public CustomLogoutHandler(LoggerService loggerService) {
        this.loggerService = loggerService;
    }



    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        loggerService.addEntry(username, LoggerEntry.LoggerType.LOGOUT);
        response.sendRedirect("/login/logout");
    }
}

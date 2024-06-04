package com.uniovi.sdi2324entrega1ext205.customHandlers;


import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.services.LoggerService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenFailureHandler implements AuthenticationFailureHandler {

    private final LoggerService loggerService;

    public CustomAuthenFailureHandler(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        loggerService.addEntry(username, LoggerEntry.LoggerType.LOGIN_ERR);
        response.sendRedirect("/login?error=true");
        //response.sendRedirect("/login-error");
    }
}

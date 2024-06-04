package com.uniovi.sdi2324entrega1ext205;



import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.services.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    private final LoggerService loggerService;

    public LoggerInterceptor(LoggerService logService) {
        this.loggerService = logService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String desc =request.getMethod()+request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null) {
            desc += "?" + queryString;
        }
        loggerService.addEntry(desc, LoggerEntry.LoggerType.PET);
        //logger.info(request.getMethod()+request.getRequestURI());
        return true;
    }
}

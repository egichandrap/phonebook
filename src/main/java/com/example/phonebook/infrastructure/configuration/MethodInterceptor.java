package com.example.phonebook.infrastructure.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class MethodInterceptor implements HandlerInterceptor {

    private final String[] allowedMethods = new String[]{"TRACE"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Arrays.stream(allowedMethods).allMatch(x -> x.equalsIgnoreCase(request.getMethod()))) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.setContentType("message/http");
            response.setHeader("Allow", "GET,POST,PUT");
            response.getWriter().println(request.getMethod() + " method not allowed");
            response.getWriter().flush();
            return false;
        }
        return true;
    }
}

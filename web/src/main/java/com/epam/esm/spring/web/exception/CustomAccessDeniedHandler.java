package com.epam.esm.spring.web.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String ACCESS_DENIED_ERROR_MESSAGE = "error.access_denied";

    private final JsonResponseSender jsonResponseSender;
    private final ControllerAdvisor controllerAdvisor;

    public CustomAccessDeniedHandler(JsonResponseSender jsonResponseSender, ControllerAdvisor controllerAdvisor) {
        this.jsonResponseSender = jsonResponseSender;
        this.controllerAdvisor = controllerAdvisor;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        Object responseObject = controllerAdvisor.handleCustomAccessDeniedException(
                new CustomAccessDeniedException(ACCESS_DENIED_ERROR_MESSAGE),
                httpServletRequest.getLocale()
        );

        jsonResponseSender.send(httpServletResponse, responseObject);
    }
}

package com.epam.esm.spring.web.exception;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonResponseSender {

    private static final String RESPONSE_ENCODING = "UTF-8";
    private static final String RESPONSE_CONTENT = "application/json";

    public void send(HttpServletResponse response, Object responseObject) throws IOException {
        ResponseEntity responseEntity = (ResponseEntity) responseObject;
        Map<String, Object> responseMap = (Map<String, Object>) responseEntity.getBody();
        response.setCharacterEncoding(RESPONSE_ENCODING);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(RESPONSE_CONTENT);

        String json = new ObjectMapper().writeValueAsString(responseMap);
        response.getWriter().write(json);
        response.flushBuffer();
    }
}

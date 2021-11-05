package com.epam.esm.spring.service.util;

import com.epam.esm.spring.service.config.ConfigProperties;
import com.epam.esm.spring.service.dto.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageRequestProcessor {

    private final ConfigProperties configProperties;

    public PageRequestProcessor(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public void processRequest(Pageable pageRequest) {
        if (pageRequest.getPage() == null) {
            pageRequest.setPage(configProperties.getPage());
        }

        if (pageRequest.getSize() == null) {
            pageRequest.setSize(configProperties.getSize());
        }
    }
}
package com.epam.esm.spring.service.util;

import com.epam.esm.spring.service.config.ConfigProperties;
import com.epam.esm.spring.service.dto.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageRequestProcessor {

    private final ConfigProperties configProperties;

    public void processRequest(PageableDto pageRequest) {
        if (pageRequest.getPage() == null) {
            pageRequest.setPage(configProperties.getPage());
        }

        if (pageRequest.getSize() == null) {
            pageRequest.setSize(configProperties.getSize());
        }
    }
}
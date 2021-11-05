package com.epam.esm.spring.service.util;

import com.epam.esm.spring.service.dto.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:pagination.properties")
public class PageRequestProcessor {

    @Value("${page}")
    private int defaultPage;

    @Value("${size}")
    private int defaultSize;

    public void processRequest(Pageable pageRequest) {
        if (pageRequest.getPage() == null) {
            pageRequest.setPage(defaultPage);
        }

        if (pageRequest.getSize() == null) {
            pageRequest.setSize(defaultSize);
        }
    }
}
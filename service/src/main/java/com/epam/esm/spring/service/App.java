package com.epam.esm.spring.service;

import com.epam.esm.spring.service.config.ServiceConfig;
import com.epam.esm.spring.service.dto.TagDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
        DefaultTagService bean = appContext.getBean(DefaultTagService.class);

        System.out.println("Start...");

        List<TagDto> tags = bean.findAll();
        System.out.println(tags);
    }
}
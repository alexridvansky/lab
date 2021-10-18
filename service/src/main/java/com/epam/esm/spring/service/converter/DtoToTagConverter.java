package com.epam.esm.spring.service.converter;

import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.TagDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTagConverter implements Converter<TagDto, Tag> {
    @Override
    public Tag convert(TagDto source) {
        return Tag.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}

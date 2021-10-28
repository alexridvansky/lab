package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto extends RepresentationModel<AbstractDto> {
    private Long id;
}

package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto implements Serializable {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{2,100}+$")
    private String name;
}

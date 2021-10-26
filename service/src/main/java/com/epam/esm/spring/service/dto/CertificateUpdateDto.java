package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateUpdateDto {

    private Long id;

    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{2,100}+$")
    private String name;

    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{4,200}+$")
    private String description;

    @Positive
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.10")
    private BigDecimal price;

    @Positive
    @Min(value = 1)
    @Max(value = 365)
    private Integer duration;

    @Valid
    private List<TagDto> tagsToAdd;

    @Valid
    private List<TagDto> tagsToRemove;
}

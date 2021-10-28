package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto extends AbstractDto {

    @NotBlank
    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{2,100}+$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{4,200}+$")
    private String description;

    @NotNull
    @Positive
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.10")
    private BigDecimal price;

    @NotNull
    @Positive
    @Min(value = 1)
    @Max(value = 365)
    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    @Valid
    private List<TagDto> tags;

    @Builder
    public CertificateDto(Long id, String name, String description, BigDecimal price, Integer duration, List<TagDto> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}

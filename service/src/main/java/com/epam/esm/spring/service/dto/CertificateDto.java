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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {

    public interface Create {
    }

    public interface Update {
    }

    private Long id;

    @NotBlank(groups = {Create.class})
    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{2,100}+$", groups = {Create.class, Update.class})
    private String name;

    @NotBlank(groups = {Create.class})
    @Pattern(regexp = "^[^-,.%&$ ][a-zA-Z0-9,.%&$ ]{4,200}+$", groups = {Create.class, Update.class})
    private String description;

    @NotNull(groups = {Create.class})
    @Positive(groups = {Create.class, Update.class})
    @Digits(integer = 6, fraction = 2, groups = {Create.class, Update.class})
    @DecimalMin(value = "0.10", groups = {Create.class, Update.class})
    private BigDecimal price;

    @NotNull(groups = {Create.class})
    @Positive(groups = {Create.class, Update.class})
    @Min(value = 1, groups = {Create.class, Update.class})
    @Max(value = 365, groups = {Create.class, Update.class})
    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    @Valid
    private List<TagDto> tags;
}

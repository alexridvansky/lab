package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9,.%&$ ]")
    private String name;

    @NotBlank(groups = {Create.class})
    @Size(min = 10, max = 200)
    @Pattern(regexp = "^[a-zA-Z0-9,.%&$ ]")
    private String description;

    @NotNull(groups = {Create.class})
    @Positive
    @Digits(integer = 4, fraction = 2)
    @DecimalMin("0.50")
    private BigDecimal price;

    @NotNull(groups = {Create.class})
    @Positive
    @Min(1)
    @Max(365)
    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private List<TagDto> tags;
}

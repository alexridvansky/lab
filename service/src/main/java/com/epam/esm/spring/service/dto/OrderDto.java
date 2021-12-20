package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends AbstractDto {

    private BigDecimal total;

    private LocalDateTime purchaseDate;

    private Long userId;

    @Valid
    private List<CertificateDto> certificates;

    @Builder
    public OrderDto(Long id, BigDecimal total, Long userId, List<CertificateDto> certificates) {
        super(id);
        this.total = total;
        this.userId = userId;
        this.certificates = certificates;
    }
}

package com.epam.esm.spring.service.dto;

import com.epam.esm.spring.repository.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends AbstractDto {

    private BigDecimal total;

    private LocalDateTime purchaseDate;

    @Valid
    private User user;

    @Valid
    private List<CertificateDto> certificates;

    @Builder
    public OrderDto(Long id, BigDecimal total, User user, List<CertificateDto> certificates) {
        super(id);
        this.total = total;
        this.user = user;
        this.certificates = certificates;
    }
}

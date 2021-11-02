package com.epam.esm.spring.service.dto;

import com.epam.esm.spring.repository.model.User;
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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends AbstractDto {

    private BigDecimal total;

    private LocalDateTime purchaseDate;

    @Valid
    private UserDto userDto;

    @Valid
    private List<CertificateDto> certificates;

    @Builder
    public OrderDto(Long id, BigDecimal total, UserDto userDto, List<CertificateDto> certificates) {
        super(id);
        this.total = total;
        this.userDto = userDto;
        this.certificates = certificates;
    }
}

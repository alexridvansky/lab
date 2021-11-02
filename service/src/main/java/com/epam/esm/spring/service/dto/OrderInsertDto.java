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
public class OrderInsertDto extends AbstractDto {

    private LocalDateTime purchaseDate;

    @Valid
    private User user;

    @Valid
    private List<CertificateUpdateDto> certificates;

    @Builder
    public OrderInsertDto(Long id, User user, List<CertificateUpdateDto> certificates) {
        super(id);
        this.user = user;
        this.certificates = certificates;
    }
}

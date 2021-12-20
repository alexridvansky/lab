package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto extends AbstractDto {

    private LocalDateTime purchaseDate;

    @Positive
    private Long userId;

    @Valid
    private List<CertificateUpdateDto> certificates;

    @Builder
    public OrderInsertDto(Long id, Long userId, List<CertificateUpdateDto> certificates) {
        super(id);
        this.userId = userId;
        this.certificates = certificates;
    }
}

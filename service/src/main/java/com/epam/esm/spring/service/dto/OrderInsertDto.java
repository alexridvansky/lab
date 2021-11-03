package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto extends AbstractDto {

    private LocalDateTime purchaseDate;

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

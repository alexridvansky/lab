package com.epam.esm.spring.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Order extends AbstractEntity {

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_certificate_xref",
        joinColumns = {
            @JoinColumn(name = "order_id")},
        inverseJoinColumns = {
            @JoinColumn(name = "certificate_id")})
    private List<Certificate> certificates;

    @PrePersist
    void prePersistBatch() {
        this.purchaseDate = LocalDateTime.now();
    }
}
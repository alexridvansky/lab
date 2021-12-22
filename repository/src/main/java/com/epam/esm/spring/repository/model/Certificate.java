package com.epam.esm.spring.repository.model;

import lombok.EqualsAndHashCode;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "gift_certificate")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Certificate extends AbstractEntity {

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(scale = 2)
    private BigDecimal price;

    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "certificate_tag_xref",
            joinColumns = {
                    @JoinColumn(name = "certificate_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @PrePersist
    private void prePersistBatch() {
        createDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdateBatch() {
        lastUpdateDate = LocalDateTime.now();
    }

}

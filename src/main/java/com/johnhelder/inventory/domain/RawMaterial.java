package com.johnhelder.inventory.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "raw_materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(length = 100)
    private String category;

    @Column
    private Integer minimumQuantity;

    @Column(length = 20)
    private String unit;

    @Column(length = 100)
    private String location;

    @Column(precision = 15, scale = 2)
    private BigDecimal unitPrice;

}

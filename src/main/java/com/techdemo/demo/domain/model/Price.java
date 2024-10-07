package com.techdemo.demo.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter private Long brandId;
    @Getter @Setter private LocalDateTime startDate;
    @Getter @Setter private LocalDateTime endDate;
    @Getter @Setter private Long priceList;
    @Getter @Setter private Long productId;
    @Getter @Setter private Integer priority;
    @Getter @Setter private Double price;
    @Getter @Setter private String curr;

    // Getters and Setters

}
package com.techdemo.demo.web.controller;

import com.techdemo.demo.application.service.PricesService;
import com.techdemo.demo.domain.model.Prices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/prices")
public class PricesController {

    private final PricesService pricesService;

    public PricesController(PricesService pricesService) {
        this.pricesService = pricesService;
    }

    @GetMapping
    public ResponseEntity<Prices> getPrice(
            @RequestParam("applicationDate") String applicationDateStr,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Integer brandId) {

        LocalDateTime applicationDate = LocalDateTime.parse(applicationDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        Optional<Prices> price = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        return price.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}



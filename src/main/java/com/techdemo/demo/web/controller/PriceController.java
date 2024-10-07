package com.techdemo.demo.web.controller;

import com.techdemo.demo.application.service.PriceService;
import com.techdemo.demo.domain.model.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/prices")
    public Optional<Price> getPrice(@RequestParam Long productId, @RequestParam Long brandId, @RequestParam String applicationDate) {
        return null;
    }
}

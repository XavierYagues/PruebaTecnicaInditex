package com.techdemo.demo.application.service;

import com.techdemo.demo.domain.model.Prices;
import com.techdemo.demo.domain.repository.PricesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PricesService {

    private final PricesRepository pricesRepository;

    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    public Optional<Prices> getPriceByDateProductAndBrand(LocalDateTime applicationDate, Long productId, Integer brandId) {
        List<Prices> pricesList = pricesRepository.findByProductIdAndBrandId(productId, brandId);

        return pricesList.stream()
                .filter(price -> applicationDate.isAfter(price.getStartDate()) && applicationDate.isBefore(price.getEndDate()))
                .max(Comparator.comparingInt(Prices::getPriority));
    }
}


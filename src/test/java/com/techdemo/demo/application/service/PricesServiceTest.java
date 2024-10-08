package com.techdemo.demo.application.service;

import com.techdemo.demo.domain.model.Prices;
import com.techdemo.demo.domain.repository.PricesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PricesServiceTest {

    private PricesRepository pricesRepository;
    private PricesService pricesService;

    @BeforeEach
    void setUp() {
        pricesRepository = mock(PricesRepository.class);
        pricesService = new PricesService(pricesRepository);
    }

    @Test
    void testGetPriceByDateProductAndBrand() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        Prices price1 = new Prices();
        price1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price1.setPrice(35.50);
        price1.setPriority(0);

        Prices price2 = new Prices();
        price2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0));
        price2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30));
        price2.setPrice(25.45);
        price2.setPriority(1);

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId)).thenReturn(List.of(price1, price2));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(35.50, result.get().getPrice());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }

    @Test
    void testSelectPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Integer brandId = 1;
        Double expectedPrice = 40.00;


        Prices price1 = new Prices();
        price1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price1.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        price1.setPrice(30.00);
        price1.setPriority(0);

        Prices price2 = new Prices();
        price2.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price2.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        price2.setPrice(35.50);
        price2.setPriority(1);

        Prices price3 = new Prices();
        price3.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price3.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        price3.setPrice(40.00);
        price3.setPriority(2);

        // Mocking repository to return a list of 3 prices with the same date range
        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1, price2, price3));

        // The service should select the one with the highest priority (price3)
        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(expectedPrice, result.get().getPrice());
        assertEquals(2, result.get().getPriority());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }
}


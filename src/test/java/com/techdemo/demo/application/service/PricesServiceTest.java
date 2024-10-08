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
    private Prices price1, price2, price3, price4;

    @BeforeEach
    void setUp() {
        pricesRepository = mock(PricesRepository.class);
        pricesService = new PricesService(pricesRepository);

        price1 = new Prices();
        price1.setBrandId(1);
        price1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price1.setPriceList(1);
        price1.setProductId(35455L);
        price1.setPrice(35.50);
        price1.setPriority(0);

        price2 = new Prices();
        price2.setBrandId(1);
        price2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0));
        price2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30));
        price2.setPriceList(2);
        price2.setProductId(35455L);
        price2.setPrice(25.45);
        price2.setPriority(1);

        price3 = new Prices();
        price3.setBrandId(1);
        price3.setStartDate(LocalDateTime.of(2020, 6, 15, 0, 0));
        price3.setEndDate(LocalDateTime.of(2020, 6, 15, 11, 0));
        price3.setPriceList(3);
        price3.setProductId(35455L);
        price3.setPrice(30.50);
        price3.setPriority(1);

        price4 = new Prices();
        price4.setBrandId(1);
        price4.setStartDate(LocalDateTime.of(2020, 6, 15, 16, 0));
        price4.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price4.setPriceList(4);
        price4.setProductId(35455L);
        price4.setPrice(38.95);
        price4.setPriority(1);
    }



    @Test
    void testGetPriceByDateProductAndBrand() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId)).thenReturn(List.of(price1, price2));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(35.50, result.get().getPrice());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }


    // Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetPriceAt10On14th() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1,price2,price3,price4));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(35.50, result.get().getPrice());
        assertEquals(0, result.get().getPriority());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }

    // Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetPriceAt16On14th() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1,price2,price3,price4));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(25.45, result.get().getPrice());
        assertEquals(1, result.get().getPriority());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }

    // Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetPriceAt21On14th() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1,price2,price3,price4));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(35.50, result.get().getPrice());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }

    // Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetPriceAt10On15th() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1,price2,price3,price4));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(30.50, result.get().getPrice());
        assertEquals(1, result.get().getPriority());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }

    // Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetPriceAt21On16th() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        when(pricesRepository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(price1,price2,price3,price4));

        Optional<Prices> result = pricesService.getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        assertEquals(38.95, result.get().getPrice());
        assertEquals(1, result.get().getPriority());
        verify(pricesRepository, times(1)).findByProductIdAndBrandId(productId, brandId);
    }
}


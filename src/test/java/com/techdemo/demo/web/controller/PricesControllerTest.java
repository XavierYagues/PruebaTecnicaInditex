package com.techdemo.demo.web.controller;

import com.techdemo.demo.application.service.PricesService;
import com.techdemo.demo.domain.model.Prices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PricesController.class)
class PricesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesService pricesService;

    private Prices samplePrice;

    @BeforeEach
    void setUp() {
        samplePrice = new Prices();
        samplePrice.setProductId(35455L);
        samplePrice.setBrandId(1);
        samplePrice.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        samplePrice.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        samplePrice.setPrice(35.50);
        samplePrice.setPriority(0);
    }

    @Test
    void test1_getPriceAt10On14th() throws Exception {
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.of(samplePrice));

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void test2_getPriceAt16On14th() throws Exception {
        samplePrice.setPrice(25.45);
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.of(samplePrice));

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void test3_getPriceAt21On14th() throws Exception {
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test4_getPriceAt10On15th() throws Exception {
        samplePrice.setPrice(30.50);
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.of(samplePrice));

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void test5_getPriceAt21On16th() throws Exception {
        samplePrice.setPrice(38.95);
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.of(samplePrice));

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95));
    }
}

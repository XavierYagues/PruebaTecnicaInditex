package com.techdemo.demo.integration;

import com.techdemo.demo.application.service.PricesService;
import com.techdemo.demo.domain.model.Prices;
import com.techdemo.demo.domain.repository.PricesRepository;
import com.techdemo.demo.web.controller.PricesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PricesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesRepository pricesRepository;

    @InjectMocks
    private PricesService pricesService;

    private Prices price1, price2, price3, price4;

    @BeforeEach
    void setUp() {
        // Simulamos múltiples Prices con la misma fecha de inicio y final pero diferentes prioridades
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
    void testControllerIntegration() throws Exception {
        when(pricesRepository.findByProductIdAndBrandId(35455L, 1))
                .thenReturn(List.of(price1, price2, price3, price4));

        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-20T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.priority").value(1));
    }
}

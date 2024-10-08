package com.techdemo.demo.web.controller;

import com.techdemo.demo.application.service.PricesService;
import com.techdemo.demo.domain.model.Prices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
        // Configuración de un objeto Prices de ejemplo para simular la respuesta del servicio
        samplePrice = new Prices();
        samplePrice.setProductId(35455L);
        samplePrice.setBrandId(1);
        samplePrice.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        samplePrice.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        samplePrice.setPrice(35.50);
        samplePrice.setPriority(0);
    }

    @Test
    void testGetPriceByProductAndBrandBasic() throws Exception {
        // Mockeamos la respuesta del servicio para devolver el samplePrice
        when(pricesService.getPriceByDateProductAndBrand(any(LocalDateTime.class), any(Long.class), any(Integer.class)))
                .thenReturn(Optional.of(samplePrice));

        // Simulamos una solicitud HTTP GET al endpoint del controlador
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                // Verificamos que la respuesta tenga un código 200 (OK)
                .andExpect(status().isOk())
                // Verificamos que los valores devueltos sean los esperados
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.priority").value(0));
    }
}

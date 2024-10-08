package com.techdemo.demo.domain.repository;

import com.techdemo.demo.domain.model.Prices;
import java.util.List;

public interface PricesRepository {
    List<Prices> findByProductIdAndBrandId(Long productId, Integer brandId);
}

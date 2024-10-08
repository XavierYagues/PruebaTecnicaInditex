package com.techdemo.demo.domain.repository;

import com.techdemo.demo.domain.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricesRepository extends JpaRepository <Prices, Long>{
    List<Prices> findByProductIdAndBrandId(Long productId, Integer brandId);
}

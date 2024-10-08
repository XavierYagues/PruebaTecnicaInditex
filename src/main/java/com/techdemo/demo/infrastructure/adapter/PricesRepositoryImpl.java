package com.techdemo.demo.infrastructure.adapter;

import com.techdemo.demo.domain.model.Prices;
import com.techdemo.demo.domain.repository.PricesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PricesRepositoryImpl extends JpaRepository<Prices, Long>, PricesRepository {
    List<Prices> findByProductIdAndBrandId(Integer productId, Integer brandId);
}




/*import com.techdemo.demo.domain.model.Prices;
import com.techdemo.demo.domain.repository.PricesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class PricesRepositoryImpl implements PricesRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Prices> findByProductIdAndBrandId(Long productId, Long brandId) {
        String query = "SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId";
        return entityManager.createQuery(query, Prices.class)
                .setParameter("productId", productId)
                .setParameter("brandId", brandId)
                .getResultList();
    }
}
*/


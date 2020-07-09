package com.example.demo.repositories.acquiredProducts;

import com.example.demo.model.acquiredProduct.AcquiredProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquiredProductRepository extends JpaRepository<AcquiredProduct, Long> {
}

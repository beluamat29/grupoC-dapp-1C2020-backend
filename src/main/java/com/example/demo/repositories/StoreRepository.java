package com.example.demo.repositories;
import com.example.demo.model.store.Store;

import com.example.demo.model.store.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT * FROM (SELECT * FROM STORE_STORE_CATEGORIES INNER JOIN STORE ON STORE.ID=STORE_ID) WHERE STORE_CATEGORIES= :category",
            nativeQuery = true)
    List<Store> getStoresWithACategory(@Param("category") String category);

    //List<Store> getStoresWithACategory(StoreCategory grocery);
}

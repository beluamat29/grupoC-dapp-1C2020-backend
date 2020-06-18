package com.example.demo.repositories.merchandise;

import com.example.demo.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {

    @Query(value = "SELECT * FROM (SELECT * FROM STORE_MERCHANDISE_LIST INNER JOIN MERCHANDISE ON MERCHANDISE.ID=MERCHANDISE_LIST_ID AND STORE_ID= :storeId)",
            nativeQuery = true)
    Optional<List<Merchandise>> getMerchandiseFromStore(@Param("storeId") Long storeId);

}

//SELECT * FROM (SELECT * FROM STORE_MERCHANDISE_LIST INNER JOIN MERCHANDISE ON MERCHANDISE.ID=STORE_MERCHANDISE_LIST.MERCHANDISE_LIST_ID) WHERE STORE_ID=27

package com.example.demo.repositories.users;

import com.example.demo.model.user.StoreAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAdminRepository extends JpaRepository<StoreAdminUser, Long> {
    StoreAdminUser findByStoreId(Long id);
}

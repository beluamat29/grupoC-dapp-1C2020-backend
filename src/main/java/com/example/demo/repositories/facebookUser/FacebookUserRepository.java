package com.example.demo.repositories.facebookUser;

import com.example.demo.model.user.FacebookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacebookUserRepository extends JpaRepository<FacebookUser, Long> {
}

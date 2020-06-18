package com.example.demo.repositories.users;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameEquals(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}

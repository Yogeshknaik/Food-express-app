package com.rktechyt.ecommerce.repository;

import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rktechyt.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findUserByEmail(String email);

    User findByEmail(@NotEmpty @Email(message = "{errors.invalid_email}") String email);
}

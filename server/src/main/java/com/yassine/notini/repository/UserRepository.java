package com.yassine.notini.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yassine.notini.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}

package com.komarov.patel.research.methodology.esportservice.repository;

import com.komarov.patel.research.methodology.esportservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

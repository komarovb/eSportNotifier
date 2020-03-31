package com.komarov.patel.research.methodology.esportservice.repository;

import com.komarov.patel.research.methodology.esportservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

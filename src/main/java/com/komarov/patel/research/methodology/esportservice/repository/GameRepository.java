package com.komarov.patel.research.methodology.esportservice.repository;

import com.komarov.patel.research.methodology.esportservice.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByName(String name);
    Game findById(long gameId);
}

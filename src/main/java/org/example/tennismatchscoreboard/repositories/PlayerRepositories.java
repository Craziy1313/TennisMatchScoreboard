package org.example.tennismatchscoreboard.repositories;

import org.example.tennismatchscoreboard.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PlayerRepositories extends JpaRepository <Player, Integer> {

    Optional<Player> findByName(String name);
}

package org.example.tennismatchscoreboard.repositories;

import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchesRepositories extends JpaRepository <Matches, Integer> {

    //Optional<List<Matches>> findAllBy(Player player);
}

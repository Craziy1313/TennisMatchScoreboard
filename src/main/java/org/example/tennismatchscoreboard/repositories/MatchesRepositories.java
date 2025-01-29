package org.example.tennismatchscoreboard.repositories;

import org.example.tennismatchscoreboard.models.Matches;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchesRepositories extends JpaRepository <Matches, Integer> {

    Page<Matches> findByPlayer1IdOrPlayer2Id(Integer player1Id, Integer player2Id, Pageable pageable);

}

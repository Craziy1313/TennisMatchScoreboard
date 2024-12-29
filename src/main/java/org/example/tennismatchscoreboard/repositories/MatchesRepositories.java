package org.example.tennismatchscoreboard.repositories;

import org.example.tennismatchscoreboard.models.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesRepositories extends JpaRepository <Matches, Integer> {
}

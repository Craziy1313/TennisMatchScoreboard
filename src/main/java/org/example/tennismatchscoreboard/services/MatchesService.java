package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.repositories.MatchesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MatchesService {
    private final MatchesRepositories matchesRepositories;

    @Autowired
    public MatchesService(MatchesRepositories matchesRepositories) {
        this.matchesRepositories = matchesRepositories;
    }

    public void saveMatches(Matches matches) {
        matchesRepositories.save(matches);
    }

    public List <Matches> getAllMatches() {
        return matchesRepositories.findAll();
    }

}

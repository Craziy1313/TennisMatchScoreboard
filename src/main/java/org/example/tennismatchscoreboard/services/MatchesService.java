package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.repositories.MatchesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional (readOnly = true)
@Service
public class MatchesService {
    private final MatchesRepositories matchesRepositories;
    private final PlayerService playerService;

    @Autowired
    public MatchesService(MatchesRepositories matchesRepositories, PlayerService playerService) {
        this.matchesRepositories = matchesRepositories;
        this.playerService = playerService;
    }

    @Transactional
    public void saveMatches(Matches matches) {
        matchesRepositories.save(matches);
    }

    public Page<Matches> getMatches(Pageable pageable) {
        return matchesRepositories.findAll(pageable);
    }

    public Page<Matches> getMatchesByPlayerName(String playerName, Pageable pageable) {

        Player player = playerService.getPlayerByName(playerName).orElse(null);

        if (player == null) {
            return Page.empty(pageable);
        }

        return matchesRepositories.findByPlayer1IdOrPlayer2Id(player.getId(), player.getId(), pageable);
    }


}

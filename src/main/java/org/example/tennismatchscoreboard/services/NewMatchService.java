package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.exception.PlayerNotFountException;
import org.example.tennismatchscoreboard.services.match_score_model.Match;
import org.example.tennismatchscoreboard.services.match_score_model.Score;
import org.example.tennismatchscoreboard.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewMatchService {

    private final PlayerService playerServices;

    private final MatchScoreCalculationService matchScoreCalculationService;

    private final OngoingMatchesService ongoingMatchesService;

    @Autowired
    public NewMatchService(PlayerService playerServices, MatchScoreCalculationService matchScoreCalculationService, OngoingMatchesService ongoingMatchesService) {
        this.playerServices = playerServices;
        this.matchScoreCalculationService = matchScoreCalculationService;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    public UUID newMatch(String player1, String player2) {

        Player playerOne = playerServices.getPlayerByName(player1).orElse(null);
        Player playerTwo = playerServices.getPlayerByName(player2).orElse(null);

        if (playerOne == null) {
            playerServices.savePlayer(new Player(player1));
        }

        if (playerTwo == null) {
            playerServices.savePlayer(new Player(player2));
        }

        String matchId = UUID.randomUUID().toString();
        Score score = new Score(matchScoreCalculationService);
        int player1Id = playerServices.getPlayerByName(player1).orElseThrow(PlayerNotFountException::new).getId();
        int player2Id = playerServices.getPlayerByName(player2).orElseThrow(PlayerNotFountException::new).getId();
        Match match = new Match(player1Id, player2Id, score);

        ongoingMatchesService.addMatch(UUID.fromString(matchId), match);

        return UUID.fromString(matchId);
    }
}

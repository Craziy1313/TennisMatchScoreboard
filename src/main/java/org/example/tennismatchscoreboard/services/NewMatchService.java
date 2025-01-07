package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.models.innerModel.Match;
import org.example.tennismatchscoreboard.models.innerModel.Score;
import org.example.tennismatchscoreboard.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewMatchService {

    private final PlayerService playerServices;

    private final ScoreService scoreService;

    @Autowired
    public NewMatchService(PlayerService playerServices, ScoreService scoreService) {
        this.playerServices = playerServices;
        this.scoreService = scoreService;
    }

    public UUID newMatch(String player1, String player2) {

        Player playerOne = playerServices.getPlayerByName(player1);
        Player playerTwo = playerServices.getPlayerByName(player2);

        System.out.println(playerOne);
        System.out.println(playerTwo);

        if (playerOne == null) {
            Player playerOneForSave = new Player();
            playerOneForSave.setName(player1);
            playerServices.savePlayer(playerOneForSave);
        }

        if (playerTwo == null) {
            Player playerTwoForSave = new Player();
            playerTwoForSave.setName(player2);
            playerServices.savePlayer(playerTwoForSave);
        }

        String matchId = UUID.randomUUID().toString();
        Score score = new Score(scoreService);
        Player player1Id = playerServices.getPlayerByName(player1);
        Player player2Id = playerServices.getPlayerByName(player2);
        Match match = new Match(player1Id.getId(), player2Id.getId(), score);

        Match.addMatch(UUID.fromString(matchId), match);

        return UUID.fromString(matchId);
    }
}

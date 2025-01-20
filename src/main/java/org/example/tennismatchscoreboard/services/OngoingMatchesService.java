package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.exception.PlayerNotFountException;
import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.services.match_score_model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OngoingMatchesService {

    private static Map<UUID, Match> players;

    private final PlayerService playerServices;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    static {
            players = new HashMap<>();
    }

    @Autowired
    public OngoingMatchesService(PlayerService playerServices, FinishedMatchesPersistenceService finishedMatchesPersistenceService) {
        this.playerServices = playerServices;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
    }


    public void updateMatchInfo(UUID uuid, Model model, String winnerName) {
        Match match = getMatch(uuid);
        Player player1 = playerServices.getPlayerById(match.getPlayer1ID()).orElseThrow(PlayerNotFountException::new);
        Player player2 = playerServices.getPlayerById(match.getPlayer2ID()).orElseThrow(PlayerNotFountException::new);

        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);
        model.addAttribute("matchId", uuid);
        model.addAttribute("score", match.getScore());

        if (winnerName != null) {
            model.addAttribute("winnerName", winnerName); // Добавляем имя победителя, если есть
        }
        System.out.println(model);
    }

    public String winnerCheck(int winnerId, UUID uuid) {

        Match match = getMatch(uuid);
        String winnerName = null;

        if (winnerId == match.getPlayer1ID()) {
            match.getScore().winPlayerOne();
        } else if (winnerId == match.getPlayer2ID()) {
            match.getScore().winPlayerTwo();
        }

        if (match.getScore().isMatchOver()) {
            if (winnerId == match.getPlayer1ID()) {
                winnerName = playerServices.getPlayerById(
                        match.getPlayer1ID()).orElseThrow(PlayerNotFountException::new).getName();

            } else if (winnerId == match.getPlayer2ID()) {
                winnerName = playerServices.getPlayerById(
                        match.getPlayer2ID()).orElseThrow(PlayerNotFountException::new).getName();
            }
            finishedMatchesPersistenceService.endGame(match, winnerName);
        }

        System.out.println("Winner name: " + winnerName);

        return winnerName;
    }

    public void addMatch(UUID matchId, Match match) {
        players.put(matchId, match);
    }

    public Match getMatch(UUID matchId) {
        return players.get(matchId);
    }
}

package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.exception.PlayerNotFountException;
import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.models.innerModel.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.UUID;

@Service
public class CurrentMatchService {

    private final PlayerService playerServices;
    private final EndGameService endGameService;

    @Autowired
    public CurrentMatchService(PlayerService playerServices, EndGameService endGameService) {
        this.playerServices = playerServices;
        this.endGameService = endGameService;
    }


    public void updateMatchInfo(UUID uuid, Model model, String winnerName) {
        Match match = Match.getMatch(uuid);
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

        Match match = Match.getMatch(uuid);
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
            endGameService.endGame(match, winnerName);
        }

        System.out.println("Winner name: " + winnerName);

        return winnerName;
    }
}

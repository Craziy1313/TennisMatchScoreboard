package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.exception.PlayerNotFountException;
import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.models.innerModel.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EndGameService {


    private final PlayerService playerService;
    private final MatchesService matchesService;

    @Autowired
    public EndGameService(MatchesService matchesService , PlayerService playerService) {
        this.matchesService = matchesService;
        this.playerService = playerService;
    }

    public void endGame(Match match, String winnerName) {

//        Player player1 = playerService.getPlayerById(match.getPlayer1ID()).orElseThrow(PlayerNotFountException::new);
//        Player player2 = playerService.getPlayerById(match.getPlayer2ID()).orElseThrow(PlayerNotFountException::new);
//        Player winner = playerService.getPlayerByName(winnerName).orElseThrow(PlayerNotFountException::new);

        Matches newMatch = new Matches();

        newMatch.setPlayer1(playerService.getPlayerById(match.getPlayer1ID()).orElseThrow(PlayerNotFountException::new));
        newMatch.setPlayer2(playerService.getPlayerById(match.getPlayer2ID()).orElseThrow(PlayerNotFountException::new));
        newMatch.setWinner(playerService.getPlayerByName(winnerName).orElseThrow(PlayerNotFountException::new));

        System.out.println("Completed Match: " + newMatch);
        matchesService.saveMatches(newMatch);
    }
}

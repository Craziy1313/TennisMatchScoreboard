package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.models.innerModel.Match;
import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/match-score")
public class MatchScoreController {

    private final PlayerService playerServices;

    @Autowired
    public MatchScoreController(PlayerService playerServices) {
        this.playerServices = playerServices;
    }

    @GetMapping
    public String getMatchScore(@RequestParam UUID uuid, Model model) {
        Match match = Match.getMatch(uuid);
        updateMatchInfo(uuid, model, match, null); // Передаем null для победителя
        return "MatchScore";
    }

    @PostMapping
    public String updateScore(@RequestParam UUID uuid,
                              @RequestParam int winnerId,
                              Model model) {

        Match match = Match.getMatch(uuid);
        String winnerName = null; // Инициализация победителя

        // Обновление очков
        if (winnerId == match.getPlayer1ID()) {
            match.getScore().winPlayerOne();
        } else if (winnerId == match.getPlayer2ID()) {
            match.getScore().winPlayerTwo();
        };

        // Проверка на победителя
        if (match.getScore().isMatchOver()) { // Добавьте метод `isMatchOver` в модель `Score`
            if (winnerId == match.getPlayer1ID()) {
                winnerName = playerServices.getPlayerById(match.getPlayer1ID()).getName();
            } else if (winnerId == match.getPlayer2ID()) {
                winnerName = playerServices.getPlayerById(match.getPlayer2ID()).getName();
            }
        }


        System.out.println("Winner name: " + winnerName);

        // Передаем имя победителя
        updateMatchInfo(uuid, model, match, winnerName);

        return "MatchScore";
    }

    private void updateMatchInfo(UUID uuid, Model model, Match match, String winnerName) {
        Player player1 = playerServices.getPlayerById(match.getPlayer1ID());
        Player player2 = playerServices.getPlayerById(match.getPlayer2ID());

        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);
        model.addAttribute("matchId", uuid);
        model.addAttribute("score", match.getScore());

        if (winnerName != null) {
            model.addAttribute("winnerName", winnerName); // Добавляем имя победителя, если есть
        }
        System.out.println(model);
    }
}

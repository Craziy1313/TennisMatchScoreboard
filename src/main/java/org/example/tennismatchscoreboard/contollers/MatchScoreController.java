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
@RequestMapping ("/match-score")
public class MatchScoreController {

    private final PlayerService playerServices;

    @Autowired
    public MatchScoreController(PlayerService playerServices) {
        this.playerServices = playerServices;
    }

    @GetMapping
    public String matchScore(@RequestParam UUID uuid, Model model) {

        System.out.println("uuid = " + uuid);

        Match match = Match.getMatch(uuid);

        Player player1 = playerServices.getPlayerById(match.getPlayer1ID());
        Player player2 = playerServices.getPlayerById(match.getPlayer2ID());

        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);
        model.addAttribute("matchId", uuid);
        model.addAttribute("score", match.getScore());

        model.addAttribute("tieBreakPlayerOne", match.getScore().getTieBreakPlayerOne());
        model.addAttribute("tieBreakPlayerTwo", match.getScore().getTieBreakPlayerTwo());
        model.addAttribute("tieBreak", match.getScore().isTieBreak());

        System.out.println(Match.getMatch(uuid));

        return "MatchScore";

    }
    @PostMapping
    public String updateScore(@RequestParam UUID uuid,
                              @RequestParam int winnerId,
                              Model model) {

        Match match = Match.getMatch(uuid);

        if (winnerId == match.getPlayer1ID()) {
            match.getScore().winPlayerOne();
        } else if (winnerId == match.getPlayer2ID()) {
            match.getScore().winPlayerTwo();
        }

        Player player1 = playerServices.getPlayerById(match.getPlayer1ID());
        Player player2 = playerServices.getPlayerById(match.getPlayer2ID());

        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);
        model.addAttribute("matchId", uuid);
        model.addAttribute("score", match.getScore());

        model.addAttribute("tieBreakPlayerOne", match.getScore().getTieBreakPlayerOne());
        model.addAttribute("tieBreakPlayerTwo", match.getScore().getTieBreakPlayerTwo());
        model.addAttribute("isTiebreak", match.getScore().isTieBreak());

        return "MatchScore";
    }

}

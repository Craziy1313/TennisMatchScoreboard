package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.services.CurrentMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/match-score")
public class MatchScoreController {

    private final CurrentMatchService currentMatchService;

    @Autowired
    public MatchScoreController(CurrentMatchService currentMatchService) {
        this.currentMatchService = currentMatchService;
    }

    @GetMapping
    public String getMatchScore(@RequestParam UUID uuid, Model model) {

        currentMatchService.updateMatchInfo(uuid, model, null);

        return "MatchScore";
    }

    @PostMapping
    public String updateScore(@RequestParam UUID uuid, @RequestParam int winnerId, Model model) {

        String winnerName = currentMatchService.winnerCheck(winnerId, uuid);
        currentMatchService.updateMatchInfo(uuid, model, winnerName);

        return "MatchScore";
    }
}

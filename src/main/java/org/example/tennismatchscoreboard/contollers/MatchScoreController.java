package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.services.OngoingMatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/match-score")
public class MatchScoreController {

    private final OngoingMatchesService ongoingMatchesService;

    @Autowired
    public MatchScoreController(OngoingMatchesService ongoingMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
    }

    @GetMapping
    public String getMatchScore(@RequestParam UUID uuid, Model model) {

        ongoingMatchesService.updateMatchInfo(uuid, model, null);

        return "MatchScore";
    }

    @PostMapping
    public String  updateScore(@RequestParam UUID uuid, @RequestParam int winnerId, Model model) {

        String winnerName = ongoingMatchesService.winnerCheck(winnerId, uuid);
        ongoingMatchesService.updateMatchInfo(uuid, model, winnerName);

        return "MatchScore";
    }
}

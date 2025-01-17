package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.services.MatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matches")
public class EndMatchesController {

    private final MatchesService matchesService;

    @Autowired
    public EndMatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @GetMapping
    public String endMatches(Model model) {
        model.addAttribute("matches", matchesService.getAllMatches());

        return "EndMatches";
    }

//    @GetMapping("filter_by_player_name=${name}")
//    public String endMatchesByPlayerName(Model model, @PathVariable String name) {
//        model.addAttribute("matches", matchesService.getAllMatches());
//
//        return "EndMatches";
//    }
}

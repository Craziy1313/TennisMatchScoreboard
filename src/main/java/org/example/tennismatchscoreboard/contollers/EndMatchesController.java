package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.services.MatchesService;
import org.example.tennismatchscoreboard.services.match_score_model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/matches")
public class EndMatchesController {

    private final MatchesService matchesService;

    @Autowired
    public EndMatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @GetMapping
    public String endMatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Matches> matchPage = matchesService.getMatches(PageRequest.of(page, size));

        model.addAttribute("matches", matchPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", matchPage.getTotalPages());

        return "EndMatches";
    }

//    @GetMapping("filter_by_player_name=${name}")
//    public String endMatchesByPlayerName(Model model, @PathVariable String name) {
//        model.addAttribute("matches", matchesService.getAllMatches());
//
//        return "EndMatches";
//    }
}

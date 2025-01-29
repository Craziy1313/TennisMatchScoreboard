package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.models.Matches;
import org.example.tennismatchscoreboard.services.MatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String getMatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filter_by_player_name,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Matches> matchesPage;

        if (filter_by_player_name != null && !filter_by_player_name.isEmpty()) {
            matchesPage = matchesService.getMatchesByPlayerName(filter_by_player_name, pageable);
        } else {
            matchesPage = matchesService.getMatches(pageable);
        }

        model.addAttribute("matches", matchesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", matchesPage.getTotalPages());
        model.addAttribute("filterByPlayerName", filter_by_player_name);

        return "EndMatches";
    }
}

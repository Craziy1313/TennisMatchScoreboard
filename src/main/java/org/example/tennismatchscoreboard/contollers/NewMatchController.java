package org.example.tennismatchscoreboard.contollers;

import org.example.tennismatchscoreboard.services.NewMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequestMapping ("/new-match")
@Controller
public class NewMatchController {

    private final NewMatchService newMatchService;

    @Autowired
    public NewMatchController(NewMatchService newMatchService) {
        this.newMatchService = newMatchService;
    }

    @GetMapping
    public String newMatch() {
        return "StartGame";
    }

    @PostMapping
    @ResponseBody
    public RedirectView saveMatch(
            @RequestParam String player1,
            @RequestParam String player2
    ) {
        UUID matchId = newMatchService.newMatch(player1, player2);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/match-score?uuid=" + matchId);
        return redirectView;
    }
}

package org.example.tennismatchscoreboard;

import org.example.tennismatchscoreboard.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TennisMatchScoreboardApplication {

    private final PlayerService playerServices;

    @Autowired
    public TennisMatchScoreboardApplication(PlayerService playerServices) {
        this.playerServices = playerServices;

    }
    public static void main(String[] args) {

        SpringApplication.run(TennisMatchScoreboardApplication.class, args);

//        ApplicationContext context = SpringApplication.run(TennisMatchScoreboardApplication.class, args);
//        TennisMatchScoreboardApplication app = context.getBean(TennisMatchScoreboardApplication.class);
//
//        Player player = new Player();
//        player.setName("player1");
//
//
//        app.playerServices.savePlayer(player);




    }


}

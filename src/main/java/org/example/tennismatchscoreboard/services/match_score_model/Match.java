package org.example.tennismatchscoreboard.services.match_score_model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Match {

    private int player1ID;

    private int player2ID;

    private Score score;

    public Match(int player1ID, int player2ID, Score score) {
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Match{" +
                "player1ID=" + player1ID +
                ", player2ID=" + player2ID +
                ", score=" + score +
                '}';
    }
}

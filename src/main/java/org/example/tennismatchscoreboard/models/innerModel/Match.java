package org.example.tennismatchscoreboard.models.innerModel;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Match {

    private static Map<UUID, Match> players;

    private int player1ID;

    private int player2ID;

    private Score score;

    static {
        players = new HashMap<>();
    }


    public Match(int player1ID, int player2ID, Score score) {
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.score = score;
    }

    public static void addMatch(UUID matchId, Match match) {
        players.put(matchId, match);
    }

    public static Match getMatch(UUID matchId) {
        return players.get(matchId);
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

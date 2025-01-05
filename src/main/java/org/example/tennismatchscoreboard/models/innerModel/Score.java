package org.example.tennismatchscoreboard.models.innerModel;

import lombok.Getter;
import org.antlr.v4.runtime.misc.Pair;
import org.example.tennismatchscoreboard.services.ScoreEnum;
import org.example.tennismatchscoreboard.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Score {

    private HashMap<ScoreEnum,Integer> score;

    private HashMap<ScoreEnum, Boolean> playerPriority; // <boolean>

    private final ScoreService scoreService;

    /**
     * Дефолтное заполнение таблицы счета при инициализации нового матча
     */

    {
        playerPriority = new HashMap<>();
        score = new HashMap<>();

        score.put(ScoreEnum.SET_PLAYER_ONE, 0);
        score.put(ScoreEnum.SET_PLAYER_TWO, 0);
        score.put(ScoreEnum.GAME_PLAYER_ONE, 0);
        score.put(ScoreEnum.GAME_PLAYER_TWO, 0);
        score.put(ScoreEnum.POINT_PLAYER_ONE, 0);
        score.put(ScoreEnum.POINT_PLAYER_TWO, 0);
        score.put(ScoreEnum.TIE_BREAK_POINT_PLAYER_ONE, 0);
        score.put(ScoreEnum.TIE_BREAK_POINT_PLAYER_TWO, 0);

        playerPriority.put(ScoreEnum.PRIORITY_PLAYER_ONE, false);
        playerPriority.put(ScoreEnum.PRIORITY_PLAYER_TWO, false);
        playerPriority.put(ScoreEnum.TIE_BREAK, false);
    }

    @Autowired
    public Score(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public void winPlayerOne() {

        Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> scoreAfterWin = scoreService.winPlayerOne(score, playerPriority);

        score = scoreAfterWin.a;
        playerPriority = scoreAfterWin.b;
    }

    public void winPlayerTwo() {

        Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> scoreAfterWin = scoreService.winPlayerTwo(score, playerPriority);

        score = scoreAfterWin.a;
        playerPriority = scoreAfterWin.b;
    }

    public Integer getScore(ScoreEnum mapKey) {
        return score.get(mapKey);
    }

    public boolean getPriority(ScoreEnum mapKey) {
        return playerPriority.get(mapKey);
    }

}

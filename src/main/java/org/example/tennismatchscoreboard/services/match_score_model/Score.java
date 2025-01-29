package org.example.tennismatchscoreboard.services.match_score_model;

import org.example.tennismatchscoreboard.constant.ScoreEnum;
import org.example.tennismatchscoreboard.services.MatchScoreCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Score {

    private final HashMap<ScoreEnum,Integer> score;

    private final HashMap<ScoreEnum, Boolean> playerPriority; // <boolean>

    private final MatchScoreCalculationService matchScoreCalculationService;

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
        playerPriority.put(ScoreEnum.END_GAME, false);
    }

    @Autowired
    public Score(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;
    }

    public void winPlayerOne() {

        matchScoreCalculationService.winPlayerOne(score, playerPriority);
    }

    public void winPlayerTwo() {

        matchScoreCalculationService.winPlayerTwo(score, playerPriority);
    }

    public Integer getScore(ScoreEnum mapKey) {
        return score.get(mapKey);
    }

    public boolean getPriority(ScoreEnum mapKey) {
        return playerPriority.get(mapKey);
    }

    public boolean isMatchOver() {
        return playerPriority.get(ScoreEnum.END_GAME);
    }

}

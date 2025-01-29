package org.example.tennismatchscoreboard.services;

import lombok.extern.slf4j.Slf4j;
import org.example.tennismatchscoreboard.constant.ScoreEnum;
import org.example.tennismatchscoreboard.services.match_score_model.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class MatchScoreCalculationServiceTest {

    @Test
    public void tieBreakTest() {

        Score score = new Score(new MatchScoreCalculationService());

        for (int i = 0; i < 24; i++) {
            score.winPlayerOne();
        }

        for (int i = 0; i < 24; i++) {
            score.winPlayerTwo();
        }

        System.out.println(ScoreEnum.GAME_PLAYER_ONE + " " + score.getScore(ScoreEnum.GAME_PLAYER_ONE));

        System.out.println(ScoreEnum.GAME_PLAYER_TWO + " " + score.getScore(ScoreEnum.GAME_PLAYER_TWO));

        System.out.println(ScoreEnum.TIE_BREAK + " " + score.getPriority(ScoreEnum.TIE_BREAK));

        assertTrue(score.getPriority(ScoreEnum.TIE_BREAK));
    }

    @Test
    public void playerTwoWonPointWithTheScore40_40Test() {

        Score score = new Score(new MatchScoreCalculationService());

        for (int i = 0; i < 3; i++) {
            score.winPlayerOne();
        }

        for (int i = 0; i < 4; i++) {
            score.winPlayerTwo();
        }
        System.out.println(ScoreEnum.POINT_PLAYER_ONE + " " + score.getScore(ScoreEnum.POINT_PLAYER_ONE));

        System.out.println(ScoreEnum.POINT_PLAYER_TWO + " " + score.getScore(ScoreEnum.POINT_PLAYER_TWO));

        assertEquals(score.getScore(ScoreEnum.GAME_PLAYER_TWO), 0);
    }

    @Test
    public void playerTwoWonGameWithTheScore40_0Test() {

        Score score = new Score(new MatchScoreCalculationService());

        for (int i = 0; i < 4; i++) {
            score.winPlayerTwo();
        }
        System.out.println(ScoreEnum.GAME_PLAYER_TWO + " " + score.getScore(ScoreEnum.GAME_PLAYER_TWO));

        assertEquals(score.getScore(ScoreEnum.GAME_PLAYER_TWO), 1);
    }
}

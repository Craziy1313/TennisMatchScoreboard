package org.example.tennismatchscoreboard.services;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ScoreService {

    private final List<ScoreEnum> winPlayerOne;
    private final List<ScoreEnum> winPlayerTwo;

    {
        winPlayerOne = List.of(ScoreEnum.POINT_PLAYER_ONE, ScoreEnum.GAME_PLAYER_ONE, ScoreEnum.SET_PLAYER_ONE,
                ScoreEnum.POINT_PLAYER_TWO, ScoreEnum.GAME_PLAYER_TWO, ScoreEnum.SET_PLAYER_TWO,
                ScoreEnum.PRIORITY_PLAYER_ONE, ScoreEnum.PRIORITY_PLAYER_TWO, ScoreEnum.TIE_BREAK);

        winPlayerTwo = List.of(ScoreEnum.POINT_PLAYER_TWO, ScoreEnum.GAME_PLAYER_TWO, ScoreEnum.SET_PLAYER_TWO,
                ScoreEnum.POINT_PLAYER_ONE, ScoreEnum.GAME_PLAYER_ONE, ScoreEnum.SET_PLAYER_ONE,
                ScoreEnum.PRIORITY_PLAYER_TWO, ScoreEnum.PRIORITY_PLAYER_ONE, ScoreEnum.TIE_BREAK);
    }

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> winPlayerOne(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        return ScoreMethod(score, playerPriority, winPlayerOne);
    }

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> winPlayerTwo (
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        return ScoreMethod(score, playerPriority, winPlayerTwo);
    }

    /**
     * Основной алгоритм по расчету поинтов/геймов/сетов в рамках текущего матча
     * @param winPlayerList подстановка листа в данными по заполнению метода в зависимости от того, какой игрок победил
     */

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> ScoreMethod(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList) {

        if (score.get(winPlayerList.get(0)).equals(40)) {
            if (score.get(winPlayerList.get(3)) <= 30) {
                score.put(winPlayerList.get(0), 0);
                score.put(winPlayerList.get(3), 0);
                score.put(winPlayerList.get(1), score.get(winPlayerList.get(1)) + 1);

                //todo дописать блок подсчета сетов
                if (Math.abs(score.get(winPlayerList.get(1)) - (score.get(winPlayerList.get(3)))) >= 2 &&
                        score.get(winPlayerList.get(1)) >= 6) {
                    score.put(winPlayerList.get(2), score.get(winPlayerList.get(2)) + 1);
                    System.out.println("Не будет тай брейка");
                } else {
                    System.out.println("Тай брейк");
                }
                //todo ^
            } else {
                if (playerPriority.get(winPlayerList.get(6))) {
                    score.put(winPlayerList.get(0), 0);
                    score.put(winPlayerList.get(3), 0);
                    score.put(winPlayerList.get(1), score.get(winPlayerList.get(1)) + 1);
                    playerPriority.put(winPlayerList.get(6), false);
                } else if (playerPriority.get(winPlayerList.get(7))) {
                    playerPriority.put(winPlayerList.get(7), false);
                } else {
                    playerPriority.put(winPlayerList.get(6), true);
                }
            }
        } else {
            if (score.get(winPlayerList.get(0)).equals(30)) {
                score.put(winPlayerList.get(0), score.get(winPlayerList.get(0)) + 10);
            } else {
                score.put(winPlayerList.get(0), score.get(winPlayerList.get(0)) + 15);
            }
        }

        return new Pair<>(score, playerPriority);
    }

}

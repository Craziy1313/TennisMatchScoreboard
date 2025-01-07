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
                ScoreEnum.PRIORITY_PLAYER_ONE, ScoreEnum.PRIORITY_PLAYER_TWO, ScoreEnum.TIE_BREAK,
                ScoreEnum.TIE_BREAK_POINT_PLAYER_ONE, ScoreEnum.TIE_BREAK_POINT_PLAYER_TWO, ScoreEnum.TIE_BREAK);

        winPlayerTwo = List.of(ScoreEnum.POINT_PLAYER_TWO, ScoreEnum.GAME_PLAYER_TWO, ScoreEnum.SET_PLAYER_TWO,
                ScoreEnum.POINT_PLAYER_ONE, ScoreEnum.GAME_PLAYER_ONE, ScoreEnum.SET_PLAYER_ONE,
                ScoreEnum.PRIORITY_PLAYER_TWO, ScoreEnum.PRIORITY_PLAYER_ONE, ScoreEnum.TIE_BREAK,
                ScoreEnum.TIE_BREAK_POINT_PLAYER_TWO, ScoreEnum.TIE_BREAK_POINT_PLAYER_ONE, ScoreEnum.TIE_BREAK);
    }

    //todo для тайбрейка напишем отдельный метод,
    // в методах победы игрока сначала будем проверять есть ли флаг на тайбрейке и тогда уходить в другой метод подсчета
    // механика тайбрейка
    // первый у кого будет 7 очков побеждает, проверку надо делать после приплюсования очков,
    // проверка нужна стал ли счет 6-6, если стал, то уходим в тайбрейк

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> winPlayerOne(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        if (playerPriority.get(ScoreEnum.TIE_BREAK)) {
            return tieBreak(score, playerPriority, winPlayerOne);
        }
        return scoreMethod(score, playerPriority, winPlayerOne);
    }

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> winPlayerTwo (
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        if (playerPriority.get(ScoreEnum.TIE_BREAK)) {
            return tieBreak(score, playerPriority, winPlayerTwo);
        }
        return scoreMethod(score, playerPriority, winPlayerTwo);
    }

    /**
     * Основной алгоритм по расчету поинтов/геймов/сетов в рамках текущего матча
     * @param winPlayerList подстановка листа в данными по заполнению метода в зависимости от того, какой игрок победил
     */

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> scoreMethod(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList) {

        if (score.get(winPlayerList.get(0)).equals(40)) {
            if (score.get(winPlayerList.get(3)) <= 30) {
                score.put(winPlayerList.get(0), 0);
                score.put(winPlayerList.get(3), 0);
                score.put(winPlayerList.get(1), score.get(winPlayerList.get(1)) + 1);

                //блок подсчета сетов
                if (score.get(winPlayerList.get(1)).equals(6) && score.get(winPlayerList.get(4)).equals(6)) {
                    playerPriority.put(winPlayerList.get(11), true);
                } else if (score.get(winPlayerList.get(1)).equals(7)) {
                    score.put(winPlayerList.get(0), 0);
                    score.put(winPlayerList.get(1), 0);
                    score.put(winPlayerList.get(3), 0);
                    score.put(winPlayerList.get(4), 0);
                    score.put(winPlayerList.get(2), score.get(winPlayerList.get(2)) + 1);
                }
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

    public Pair<HashMap<ScoreEnum, Integer>, HashMap<ScoreEnum, Boolean>> tieBreak(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList){
        score.put(winPlayerList.get(9), score.get(winPlayerList.get(9)) + 1);

        if (score.get(winPlayerList.get(9)).equals(7)) {
            score.put(winPlayerList.get(0), 0);
            score.put(winPlayerList.get(1), 0);
            score.put(winPlayerList.get(3), 0);
            score.put(winPlayerList.get(4), 0);

            score.put(winPlayerList.get(9), 0);
            score.put(winPlayerList.get(10), 0);

            score.put(winPlayerList.get(2), score.get(winPlayerList.get(2)) + 1);
            playerPriority.put(winPlayerList.get(11), false);
        }
        return new Pair<>(score, playerPriority);
    }
}

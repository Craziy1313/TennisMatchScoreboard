package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.constant.ScoreEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.example.tennismatchscoreboard.constant.ScoreEnum.*;
import static org.example.tennismatchscoreboard.constant.CaseEnum.*;
import static org.example.tennismatchscoreboard.constant.ServiceConstant.*;


@Service
public class MatchScoreCalculationService  {

    private static final List<ScoreEnum> winPlayerOne;
    private static final List<ScoreEnum> winPlayerTwo;

    static {
        winPlayerOne = List.of(POINT_PLAYER_ONE, GAME_PLAYER_ONE, SET_PLAYER_ONE,
                POINT_PLAYER_TWO, GAME_PLAYER_TWO, SET_PLAYER_TWO,
                PRIORITY_PLAYER_ONE, PRIORITY_PLAYER_TWO, TIE_BREAK,
                TIE_BREAK_POINT_PLAYER_ONE, TIE_BREAK_POINT_PLAYER_TWO,
                END_GAME);

        winPlayerTwo = List.of(POINT_PLAYER_TWO, GAME_PLAYER_TWO, SET_PLAYER_TWO,
                POINT_PLAYER_ONE, GAME_PLAYER_ONE, SET_PLAYER_ONE,
                PRIORITY_PLAYER_TWO, PRIORITY_PLAYER_ONE, TIE_BREAK,
                TIE_BREAK_POINT_PLAYER_TWO, TIE_BREAK_POINT_PLAYER_ONE,
                END_GAME);
    }
   /**
    * @param score - счет матча
    * @param playerPriority - приоритет игроков
    *
    *
     В методах победы игрока сначала будем проверять есть ли флаг на Тай-брейке и тогда уходить в другой метод подсчета
     механика Тай-брейка
     первый у кого будет 7 очков побеждает, проверку надо делать после приплюсования очков,
     Делаем проверку стал ли счет по Геймам 6-6 если да, то уходим в тай брейк, переключаем флаг TIE_BREAK_FLAG

    */
    public void winPlayerOne(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        if (playerPriority.get(TIE_BREAK)) {
            tieBreak(score, playerPriority, winPlayerOne);
        } else
            pointScoreMethod(score, playerPriority, winPlayerOne);
    }

    public void winPlayerTwo (
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority) {

        if (playerPriority.get(TIE_BREAK)) {
            tieBreak(score, playerPriority, winPlayerTwo);
        } else
            pointScoreMethod(score, playerPriority, winPlayerTwo);
    }

    /**
     * Основной алгоритм по расчету поинтов/геймов/сетов в рамках текущего матча
     * @param winPlayerList подстановка листа в данными по заполнению метода в зависимости от того, какой игрок победил
     */

    private void pointScoreMethod(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList) {

            switch (score.get(winPlayerList.get(POINT_WIN_PLAYER.getValue()))) {
                case LOVE:
                    score.put(winPlayerList.get(POINT_WIN_PLAYER.getValue()), FIRST_POINT);
                    break;
                case FIRST_POINT:
                    score.put(winPlayerList.get(POINT_WIN_PLAYER.getValue()), SECOND_POINT);
                    break;
                case SECOND_POINT:
                    score.put(winPlayerList.get(POINT_WIN_PLAYER.getValue()), THIRD_POINT);
                    break;
                case THIRD_POINT:
                    thirdPointScoreMethod(score, playerPriority, winPlayerList);
                    break;
            }
        }

    /**
     * Метод для подсчета при получении игроком 3 очка в гейме
     */

    private void thirdPointScoreMethod (
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList){

        if (score.get(winPlayerList.get(POINT_LOSE_PLAYER.getValue())) <= SECOND_POINT) {
            playerWhoScoresThirdPointWinsTheGame(score, playerPriority, winPlayerList);
        } else {
            playerEarnsThirdPointTheGamContinues(score, playerPriority, winPlayerList);
        }
    }

    /**
     * Метод для подсчета если получение 3 очка в гейме заканчивает гейм
     */

    private void playerWhoScoresThirdPointWinsTheGame(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList) {
        playerWinGame(score, winPlayerList);

        if (score.get(winPlayerList.get(GAME_WIN_PLAYER.getValue())).equals(6) &&
                score.get(winPlayerList.get(GAME_LOSE_PLAYER.getValue())).equals(6))
        {
            playerPriority.put(winPlayerList.get(TIE_BREAK_FLAG.getValue()), true);
        } else if (score.get(winPlayerList.get(GAME_WIN_PLAYER.getValue())).equals(7)) {

            playerWinSet(score, winPlayerList);

            if (score.get(winPlayerList.get(SET_WIN_PLAYER.getValue())).equals(2)) {
                playerPriority.put(winPlayerList.get(END_GAME_FLAG.getValue()), true);
            }
        }
    }

    /**
     * Метод для подсчета если получение 3 очка в гейме не заканчивает гейм
     */

    private void playerEarnsThirdPointTheGamContinues (
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList
    ){
        if (playerPriority.get(winPlayerList.get(PRIORITY_WIN_PLAYER.getValue()))) {

            playerWinGame(score, winPlayerList);

            playerPriority.put(winPlayerList.get(PRIORITY_WIN_PLAYER.getValue()), false);
        } else if (playerPriority.get(winPlayerList.get(PRIORITY_LOSE_PLAYER.getValue()))) {
            playerPriority.put(winPlayerList.get(PRIORITY_LOSE_PLAYER.getValue()), false);
        } else {
            playerPriority.put(winPlayerList.get(PRIORITY_WIN_PLAYER.getValue()), true);
        }
    }

    /**
     * Метод для подсчета очков при Тай-брейке
     * @param winPlayerList подстановка листа в данными по заполнению метода в зависимости от того, какой игрок победил
     */

    private void tieBreak(
            HashMap <ScoreEnum, Integer> score, HashMap<ScoreEnum, Boolean> playerPriority, List<ScoreEnum> winPlayerList){

        score.put(winPlayerList.get(TIE_BREAK_POINT_WIN_PLAYER.getValue()), score.get(winPlayerList.get(TIE_BREAK_POINT_WIN_PLAYER.getValue())) + 1);

        if (score.get(winPlayerList.get(TIE_BREAK_POINT_WIN_PLAYER.getValue())).equals(POINT_TO_WIN)) {

            playerWinSet(score, winPlayerList);

            score.put(winPlayerList.get(TIE_BREAK_POINT_WIN_PLAYER.getValue()), RESET_POINT);
            score.put(winPlayerList.get(TIE_BREAK_POINT_LOSE_PLAYER.getValue()), RESET_POINT);

            playerPriority.put(winPlayerList.get(TIE_BREAK_FLAG.getValue()), false);

            if (score.get(winPlayerList.get(SET_WIN_PLAYER.getValue())).equals(2)) {
                playerPriority.put(winPlayerList.get(END_GAME_FLAG.getValue()), true);
            }
        }
    }

    /**
     *Метод для заполнения очков при победе в Гейме одного из игроков
     */

    private void playerWinGame(
            HashMap <ScoreEnum, Integer> score, List<ScoreEnum> winPlayerList) {

        score.put(winPlayerList.get(POINT_WIN_PLAYER.getValue()), RESET_POINT);
        score.put(winPlayerList.get(POINT_LOSE_PLAYER.getValue()), RESET_POINT);
        score.put(winPlayerList.get(GAME_WIN_PLAYER.getValue()), score.get(winPlayerList.get(GAME_WIN_PLAYER.getValue())) + 1);

    }

    /**
     *Метод для заполнения очков при победе в Сете одного из игроков
     */

    private void playerWinSet(
            HashMap <ScoreEnum, Integer> score, List<ScoreEnum> winPlayerList) {

        score.put(winPlayerList.get(POINT_WIN_PLAYER.getValue()), RESET_POINT);
        score.put(winPlayerList.get(GAME_WIN_PLAYER.getValue()), RESET_GAME);
        score.put(winPlayerList.get(POINT_LOSE_PLAYER.getValue()), RESET_POINT);
        score.put(winPlayerList.get(GAME_LOSE_PLAYER.getValue()), RESET_GAME);
        score.put(winPlayerList.get(SET_WIN_PLAYER.getValue()), score.get(winPlayerList.get(SET_WIN_PLAYER.getValue())) + 1);
    }
}

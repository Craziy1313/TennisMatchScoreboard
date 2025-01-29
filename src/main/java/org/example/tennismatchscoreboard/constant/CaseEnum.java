package org.example.tennismatchscoreboard.constant;

public enum CaseEnum {

    SET_WIN_PLAYER ("Сеты победившего игрока", 2),
    SET_LOSE_PLAYER ("Сеты проигравшего игрока", 5),
    GAME_WIN_PLAYER ("Геймы победившего игрока", 1),
    GAME_LOSE_PLAYER ("Геймы проигравшего игрока", 4),
    POINT_WIN_PLAYER ("Поинты победившего игрока", 0),
    POINT_LOSE_PLAYER ("Поинты проигравшего игрока", 3),
    TIE_BREAK_POINT_WIN_PLAYER ("Очки тай брейка победившего игрока", 9),
    TIE_BREAK_POINT_LOSE_PLAYER ("Очки тай брейка проигравшего игрока", 10),

    PRIORITY_WIN_PLAYER ("Приоритет победившего игрока", 6),
    PRIORITY_LOSE_PLAYER ("Приоритет проигравшего игрока", 7),
    TIE_BREAK_FLAG ("Флаг тай брейка", 8),
    END_GAME_FLAG ("Флаг конца игры", 11);

    private final String setWinPlayer;
    private final int value;

    CaseEnum(String setWinPlayer, int value) {
        this.setWinPlayer = setWinPlayer;
        this.value = value;
    }

    public int getValue () {
        return value;
    }

    public String getSetWinPlayer() {
        return setWinPlayer;
    }

}

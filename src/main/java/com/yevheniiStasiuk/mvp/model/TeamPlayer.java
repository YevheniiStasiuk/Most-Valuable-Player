package com.yevheniiStasiuk.mvp.model;

import lombok.Getter;

@Getter
public class TeamPlayer extends Player{
    private final int number;
    private final String teamName;

    public TeamPlayer(String playerName, String nickname, int number, String teamName) {
        super(playerName, nickname);
        this.number = number;
        this.teamName = teamName;
    }
}

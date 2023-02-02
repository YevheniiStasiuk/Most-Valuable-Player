package com.yevheniiStasiuk.mvp.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HandballPlayer extends TeamPlayer{
    private int goalMade;
    private int goalReceived;

    public HandballPlayer(String playerName,
                          String nickname,
                          int number,
                          String teamName,
                          int goalMade,
                          int goalReceived) {
        super(playerName, nickname, number, teamName);
        this.goalMade = goalMade;
        this.goalReceived = goalReceived;
    }
}

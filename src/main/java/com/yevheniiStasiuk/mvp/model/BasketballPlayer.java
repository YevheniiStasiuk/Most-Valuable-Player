package com.yevheniiStasiuk.mvp.model;

import lombok.Getter;

@Getter
public class BasketballPlayer extends TeamPlayer{
    private final int scoredPoint;
    private final int rebound;
    private final int assist;

    public BasketballPlayer(String playerName,
                            String nickname,
                            int number,
                            String teamName,
                            int scoredPoint,
                            int rebound,
                            int assist) {
        super(playerName, nickname, number, teamName);
        this.scoredPoint = scoredPoint;
        this.rebound = rebound;
        this.assist = assist;
    }
}

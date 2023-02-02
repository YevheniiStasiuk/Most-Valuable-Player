package com.yevheniiStasiuk.mvp.model;

import lombok.Getter;

@Getter
public class Team {
    private String teamName;
    private int score;

    public Team(String teamName, int score) {
        this.teamName = teamName;
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}

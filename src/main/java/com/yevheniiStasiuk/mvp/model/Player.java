package com.yevheniiStasiuk.mvp.model;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Player {
    private final String playerName;
    private final String nickname;
    private int rating;

    public Player(String playerName, String nickname) {
        this.playerName = playerName;
        this.nickname = nickname;
        this.rating = 0;
    }

    public Player(String playerName, String nickname, int rating) {
        this(playerName, nickname);
        this.rating = rating;
    }

    public void addRating(int rating) {
        this.rating += rating;
    }
}
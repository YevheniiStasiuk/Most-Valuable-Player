package com.yevheniiStasiuk.mvp.service;

import com.yevheniiStasiuk.mvp.model.HandballPlayer;
import com.yevheniiStasiuk.mvp.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandballService{
    private final int GOAL_MADE_RATING_MODIFIER = 20;
    private final int GOAL_RECEIVED_RATING_MODIFIER = -7;

    private final TeamService teamService;

    @Autowired
    public HandballService(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<HandballPlayer> getHandballPlayers(List<String> lines) throws ArrayIndexOutOfBoundsException {
        return lines.stream().map(line -> {
            final String[] data = line.split(";");
            return new HandballPlayer(data[0],
                    data[1],
                    Integer.parseInt(data[2]),
                    data[3],
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5]));
        }).toList();
    }

    public List<Player> getCalculatedPlayers(List<HandballPlayer> handballPlayers) {
        final String teamOfWinnersName = teamService.getTeamOfWinners(handballPlayers);
        return handballPlayers.stream().map(handballPlayer -> {
            final int rating = handballPlayer.getGoalMade() * this.GOAL_MADE_RATING_MODIFIER
                    + handballPlayer.getGoalReceived() * this.GOAL_RECEIVED_RATING_MODIFIER
                    + (teamOfWinnersName.equals(handballPlayer.getTeamName()) ? teamService.WIN_RATING_MODIFIER : 0);
            return new Player(handballPlayer.getPlayerName(), handballPlayer.getNickname(), rating);
        }).toList();
    }
}

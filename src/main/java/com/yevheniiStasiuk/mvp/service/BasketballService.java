package com.yevheniiStasiuk.mvp.service;

import com.yevheniiStasiuk.mvp.model.BasketballPlayer;
import com.yevheniiStasiuk.mvp.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketballService {
    private final int SCORED_POINT_RATING_MODIFIER = 10;
    private final int REBOUND_RATING_MODIFIER = 5;
    private final int ASSIST_RATING_MODIFIER = 0;

    private final TeamService teamService;

    @Autowired
    public BasketballService(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<BasketballPlayer> getBasketballPlayers(List<String> lines) throws ArrayIndexOutOfBoundsException {
        return lines.stream().map(line -> {
            final String[] data = line.split(";");
            return new BasketballPlayer(data[0],
                    data[1],
                    Integer.parseInt(data[2]),
                    data[3],
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]));
        }).toList();
    }

    public List<Player> getCalculatedPlayers(List<BasketballPlayer> basketballPlayers) {
        final String teamOfWinnersName = teamService.getTeamOfWinners(basketballPlayers);
        return basketballPlayers.stream().map(basketballPlayer -> {
            final int rating = basketballPlayer.getScoredPoint() * SCORED_POINT_RATING_MODIFIER
                    + basketballPlayer.getRebound() * REBOUND_RATING_MODIFIER
                    + basketballPlayer.getAssist() * ASSIST_RATING_MODIFIER
                    + (teamOfWinnersName.equals(basketballPlayer.getTeamName()) ? teamService.WIN_RATING_MODIFIER : 0);
            return new Player(basketballPlayer.getPlayerName(), basketballPlayer.getNickname(), rating);
        }).toList();
    }
}

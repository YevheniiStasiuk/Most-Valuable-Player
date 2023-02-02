package com.yevheniiStasiuk.mvp.service;

import com.yevheniiStasiuk.mvp.model.BasketballPlayer;
import com.yevheniiStasiuk.mvp.model.HandballPlayer;
import com.yevheniiStasiuk.mvp.model.Team;
import com.yevheniiStasiuk.mvp.model.TeamPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    public final int WIN_RATING_MODIFIER = 10;
    private List<Team> initTeams(List<? extends TeamPlayer> players) {
        final List<Team> teams = new ArrayList<>(); //can't be immutable?
        players.forEach((player -> {
            final Team playerTeam = teams.stream()
                    .filter(team -> team.getTeamName().equals(player.getTeamName()))
                    .findAny()
                    .orElse(null);
            switch (player) {
                case BasketballPlayer basketballPlayer -> {
                    if (playerTeam == null)
                        teams.add(new Team(basketballPlayer.getTeamName(), basketballPlayer.getScoredPoint()));
                    else
                        playerTeam.addScore(basketballPlayer.getScoredPoint());
                }
                case HandballPlayer handballPlayer -> {
                    if (playerTeam == null)
                        teams.add(new Team(handballPlayer.getTeamName(), handballPlayer.getGoalMade()));
                    else
                        playerTeam.addScore(handballPlayer.getGoalMade());
                }
                default -> throw new RuntimeException("Unknown type of player");
            }
        }));
        return teams;
    }

    public String getTeamOfWinners(List<? extends TeamPlayer> players) {
        final List<Team> teams = initTeams(players);
        final Optional<Team> teamOfWinners = teams.stream().max(Comparator.comparing(Team::getScore));
        if (teamOfWinners.isPresent())
            return teamOfWinners.get().getTeamName();
        throw new RuntimeException("Can't get teamOfWinners");
    }
}

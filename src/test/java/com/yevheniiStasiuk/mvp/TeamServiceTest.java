package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.model.BasketballPlayer;
import com.yevheniiStasiuk.mvp.model.HandballPlayer;
import com.yevheniiStasiuk.mvp.model.TeamPlayer;
import com.yevheniiStasiuk.mvp.service.TeamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamServiceTest {
    private final TeamService teamService;

    @Autowired
    public TeamServiceTest(TeamService teamService) {
        this.teamService = teamService;
    }

    static Stream<Arguments> generateCommonTestData() {
        return Stream.of(
                Arguments.of(
                        List.of(new BasketballPlayer("player 1", "nick1", 4,
                                "Team A", 10, 2, 7),
                                new BasketballPlayer("player 2", "nick2", 8,
                                        "Team A", 0, 10, 0),
                                new BasketballPlayer("player 3", "nick3", 15,
                                        "Team A", 15, 10, 4),
                                new BasketballPlayer("player 4", "nick4", 16,
                                        "Team B", 20, 0, 0),
                                new BasketballPlayer("player 5", "nick5", 23,
                                        "Team B", 4, 7, 7),
                                new BasketballPlayer("player 6", "nick6", 42,
                                        "Team B", 8, 10, 0)),
                        "Team B"),
                Arguments.of(
                        List.of(new HandballPlayer("player 1", "nick1", 4,
                                "Team A", 0, 20),
                                new HandballPlayer("player 2", "nick2", 8,
                                        "Team A", 15, 20),
                                new HandballPlayer("player 3", "nick3", 15,
                                        "Team A", 10, 20),
                                new HandballPlayer("player 4", "nick4", 16,
                                        "Team A", 1, 25),
                                new HandballPlayer("player 5", "nick5", 23,
                                        "Team A", 12, 25),
                                new HandballPlayer("player 6", "nick6", 42,
                                        "Team A", 8, 25)),
                        "Team A"));
    }

    @ParameterizedTest
    @MethodSource("generateCommonTestData")
    void commonTest(List<? extends TeamPlayer> teamPlayers, String teamOfWinners) {
        Assertions.assertEquals(teamService.getTeamOfWinners(teamPlayers), teamOfWinners);
    }
}

package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.model.BasketballPlayer;
import com.yevheniiStasiuk.mvp.model.HandballPlayer;
import com.yevheniiStasiuk.mvp.model.Player;
import com.yevheniiStasiuk.mvp.service.HandballService;
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
public class HandballServiceTest {
    private final HandballService handballService;

    @Autowired
    public HandballServiceTest(HandballService handballService) {
        this.handballService = handballService;
    }

    static Stream<Arguments> generateTestDataForGetHandballPlayers() {
        return Stream.of(Arguments.of(
                List.of("player 1;nick1;4;Team A;0;20",
                        "player 2;nick2;8;Team A;15;20",
                        "player 3;nick3;15;Team A;10;20",
                        "player 4;nick4;16;Team B;1;25",
                        "player 5;nick5;23;Team B;12;25",
                        "player 6;nick6;42;Team B;8;25"),
                List.of(new HandballPlayer("player 1", "nick1", 4,
                                "Team A", 0, 20),
                        new HandballPlayer("player 2", "nick2", 8,
                                "Team A", 15, 20),
                        new HandballPlayer("player 3", "nick3", 15,
                                "Team A", 10, 20),
                        new HandballPlayer("player 4", "nick4", 16,
                                "Team B", 1, 25),
                        new HandballPlayer("player 5", "nick5", 23,
                                "Team B", 12, 25),
                        new HandballPlayer("player 6", "nick6", 42,
                                "Team B", 8, 25))));
    }
    @ParameterizedTest
    @MethodSource("generateTestDataForGetHandballPlayers")
    void getHandballPlayersTest(List<String> lines, List<HandballPlayer> expectedPlayers) {
        final List<HandballPlayer> actualPlayers = handballService.getHandballPlayers(lines);
        Assertions.assertEquals(actualPlayers.size(), expectedPlayers.size());
    }

    static Stream<Arguments> generateDataForGetCalculatedPlayersTest() {
        return Stream.of(Arguments.of(
                List.of(new HandballPlayer("player 1", "nick1", 4,
                                "Team A", 0, 20),
                        new HandballPlayer("player 2", "nick2", 8,
                                "Team A", 15, 20),
                        new HandballPlayer("player 3", "nick3", 15,
                                "Team A", 10, 20),
                        new HandballPlayer("player 4", "nick4", 16,
                                "Team B", 1, 25),
                        new HandballPlayer("player 5", "nick5", 23,
                                "Team B", 12, 25),
                        new HandballPlayer("player 6", "nick6", 42,
                                "Team B", 8, 25)),
                List.of(new Player("player 1", "nick1", -130),
                        new Player("player 2", "nick2", 170),
                        new Player("player 3", "nick3", 70),
                        new Player("player 4", "nick4", -155),
                        new Player("player 5", "nick5", 65),
                        new Player("player 6", "nick6", -15))));
    }

    @ParameterizedTest
    @MethodSource("generateDataForGetCalculatedPlayersTest")
    void getCalculatedPlayersTest(List<HandballPlayer> handballPlayers, List<Player> expectedPlayers) {
        final List<Player> actualPlayers = handballService.getCalculatedPlayers(handballPlayers);
        for (int i = 0; i < actualPlayers.size(); i++) {
            Assertions.assertTrue(actualPlayers.get(i).getNickname().equals(expectedPlayers.get(i).getNickname())
                    && actualPlayers.get(i).getRating() == expectedPlayers.get(i).getRating());
        }
    }

    static Stream<Arguments> generateBadDataTestData() {
        return Stream.of(Arguments.of(
                List.of("player 1;nick1;4;Team A;0;20",
                        "player 2;nick2;8;Team A;15;20",
                        "player 3;nick3;15;Team A;10;20",
                        "player 4;nick4;16;Team B;25",
                        "player 5;nick5;23;Team B;12;25",
                        "player 6;nick6;42;Team B;8;25"),
                List.of("player 1;nick1;4;Team A;0;20",
                        "player 2;nick2;8;Team A;15;20",
                        "player 3;15;Team A;10;20",
                        "player 4;nick4;16;Team B;1;25",
                        "player 5;nick5;23;Team B;12;25",
                        "player 6;nick6;42;Team B;8;25")
        ));
    }

    @ParameterizedTest
    @MethodSource("generateBadDataTestData")
    void badDataTest(List<String> lines) {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> handballService.getHandballPlayers(lines));
    }
}

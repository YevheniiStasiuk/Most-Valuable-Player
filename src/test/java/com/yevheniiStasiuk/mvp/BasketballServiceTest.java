package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.model.BasketballPlayer;
import com.yevheniiStasiuk.mvp.model.Player;
import com.yevheniiStasiuk.mvp.service.BasketballService;
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
class BasketballServiceTest {

    private final BasketballService basketballService;

    @Autowired
    public BasketballServiceTest(BasketballService basketballService) {
        this.basketballService = basketballService;
    }

    static Stream<Arguments> generateTestDataForGetBasketballPlayers() {
        return Stream.of(Arguments.of(
                List.of("player 1;nick1;4;Team A;10;2;7",
                        "player 2;nick2;8;Team A;0;10;0",
                        "player 3;nick3;15;Team A;15;10;4",
                        "player 4;nick4;16;Team B;20;0;0",
                        "player 5;nick5;23;Team B;4;7;7",
                        "player 6;nick6;42;Team B;8;10;0"),
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
                                "Team B", 8, 10, 0))));
    }
    @ParameterizedTest
    @MethodSource("generateTestDataForGetBasketballPlayers")
    void getBasketballPlayersTest(List<String> lines, List<BasketballPlayer> expectedPlayers) {
        final List<BasketballPlayer> actualPlayers = basketballService.getBasketballPlayers(lines);
        Assertions.assertEquals(actualPlayers.size(), expectedPlayers.size());
    }

    static Stream<Arguments> generateDataForGetCalculatedPlayersTest() {
        return Stream.of(Arguments.of(
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
                List.of(new Player("player 1", "nick1", 110),
                        new Player("player 2", "nick2", 50),
                        new Player("player 3", "nick3", 200),
                        new Player("player 4", "nick4", 210),
                        new Player("player 5", "nick5", 85),
                        new Player("player 6", "nick6", 140))));
    }

    @ParameterizedTest
    @MethodSource("generateDataForGetCalculatedPlayersTest")
    void getCalculatedPlayersTest(List<BasketballPlayer> basketballPlayers, List<Player> expectedPlayers) {
        final List<Player> actualPlayers = basketballService.getCalculatedPlayers(basketballPlayers);
        for (int i = 0; i < actualPlayers.size(); i++) {
            Assertions.assertTrue(actualPlayers.get(i).getNickname().equals(expectedPlayers.get(i).getNickname())
                    && actualPlayers.get(i).getRating() == expectedPlayers.get(i).getRating());
        }
    }

    static Stream<Arguments> generateBadDataTestData() {
        return Stream.of(Arguments.of(
                List.of("player 1;nick1;4;Team A;6;8;5",
                        "player 3;nick3;15;Team B;3;1;11",
                        "player 5;nick5;23;5;7;1",
                        "player 2;nick2;8;Team A;7;11;0",
                        "player 4;nick4;16;Team A;2;0;5",
                        "player 6;nick6;42;Team B;6;5;7"),
                List.of("player 1;nick1;4;Team A;10;2;7",
                        "player 2;nick2;8;Team A;10;0",
                        "player 3;nick3;15;Team A;15;10;4",
                        "player 4;nick4;16;Team B;20;0;0",
                        "player 5;nick5;23;Team B;4;7;7",
                        "player 6;nick6;42;Team B;8;10;0")));
    }

    @ParameterizedTest
    @MethodSource("generateBadDataTestData")
    void badDataTest(List<String> lines) {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> basketballService.getBasketballPlayers(lines));
    }
}

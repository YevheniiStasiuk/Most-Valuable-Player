package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.service.GameReaderService;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameReaderServiceTest {

    private final GameReaderService gameReaderService;

    @Autowired
    public GameReaderServiceTest(GameReaderService gameReaderService) {
        this.gameReaderService = gameReaderService;
    }

    static Stream<Arguments> generateCommonTestData() {
        return Stream.of(
                Arguments.of("src/test/resources/Game1.txt",
                        List.of("BASKETBALL",
                                "player 1;nick1;4;Team A;10;2;7",
                                "player 2;nick2;8;Team A;0;10;0",
                                "player 3;nick3;15;Team A;15;10;4",
                                "player 4;nick4;16;Team B;20;0;0",
                                "player 5;nick5;23;Team B;4;7;7",
                                "player 6;nick6;42;Team B;8;10;0")),
                Arguments.of("src/test/resources/Game2.txt",
                        List.of("HANDBALL",
                                "player 1;nick1;4;Team A;0;20",
                                "player 2;nick2;8;Team A;15;20",
                                "player 3;nick3;15;Team A;10;20",
                                "player 4;nick4;16;Team B;1;25",
                                "player 5;nick5;23;Team B;12;25",
                                "player 6;nick6;42;Team B;8;25")),
                Arguments.of("src/test/resources/Game3.txt",
                        List.of("BASKETBALL",
                                "player 1;nick1;4;Team A;6;8;5",
                                "player 3;nick3;15;Team B;3;1;11",
                                "player 5;nick5;23;Team B;5;7;1",
                                "player 2;nick2;8;Team A;7;11;0",
                                "player 4;nick4;16;Team A;2;0;5",
                                "player 6;nick6;42;Team B;6;5;7"))
        );
    }

    @ParameterizedTest
    @MethodSource("generateCommonTestData")
    void commonTest(String fileName, List<String> expectedLines) throws IOException {
        final List<String> actualLines = gameReaderService.readGame(fileName);
        assertTrue(expectedLines.size() == actualLines.size()
                && expectedLines.containsAll(actualLines)
                && actualLines.containsAll(expectedLines));
    }
}

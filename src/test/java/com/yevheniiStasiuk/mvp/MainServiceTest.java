package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.model.Player;
import com.yevheniiStasiuk.mvp.service.MainService;
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
class MainServiceTest {
    private final MainService mainService;

    @Autowired
    public MainServiceTest(MainService mainService) {
        this.mainService = mainService;
    }

    static Stream<Arguments> generateCommonTestData() {
        return Stream.of(
                Arguments.of(List.of("src/test/resources/Game1.txt",
                                "src/test/resources/Game2.txt"),
                        new Player("player 3", "nick3", 270)),
                Arguments.of(List.of("src/test/resources/Game1.txt",
                                "src/test/resources/Game3.txt"),
                        new Player("player 4", "nick4", 240)),
                Arguments.of(List.of("src/test/resources/Game1.txt",
                                "src/test/resources/Game2.txt",
                                "src/test/resources/Game3.txt"),
                        new Player("player 2", "nick2", 355))
        );
    }

    @ParameterizedTest
    @MethodSource("generateCommonTestData")
    void commonTest(List<String> fileNames, Player expected) {
        final Player actual = mainService.playGame(fileNames);
        Assertions.assertTrue(expected.getNickname().equals(actual.getNickname())
                && expected.getRating() == actual.getRating());
    }
}

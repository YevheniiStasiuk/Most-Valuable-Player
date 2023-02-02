package com.yevheniiStasiuk.mvp.service;

import com.yevheniiStasiuk.mvp.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.yevheniiStasiuk.mvp.GameNames.BASKETBALL;
import static com.yevheniiStasiuk.mvp.GameNames.HANDBALL;

@Service
public class MainService {
    private final GameReaderService gameReaderService;
    private final BasketballService basketballService;
    private final HandballService handballService;

    @Autowired
    public MainService(GameReaderService gameReaderService,
                       BasketballService basketballService,
                       HandballService handballService) {
        this.gameReaderService = gameReaderService;
        this.basketballService = basketballService;
        this.handballService = handballService;
    }

    public Player playGame(List<String> fileNames) {
        final List<Player> finalPlayers = new ArrayList<>(); //can't be immutable?
        try {
            for (String fileName : fileNames) {
                final List<Player> currPlayers = playRound(fileName);
                currPlayers.forEach(player -> {
                    final int playerOrder = getPlayerOrderByNickname(player.getNickname(), finalPlayers);
                    if (playerOrder == -1)
                        finalPlayers.add(player);
                    else
                        finalPlayers.get(playerOrder).addRating(player.getRating());
                });
            }
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
            return null;
        }
        return getMVP(finalPlayers);
    }

    private List<Player> playRound(String fileName) throws RuntimeException, IOException {
        final List<String> lines = gameReaderService.readGame(fileName);
        final String gameName = lines.get(0);
        lines.remove(0); //remove game name from list
        if (BASKETBALL.name().equals(gameName)) {
            return basketballService.getCalculatedPlayers(basketballService.getBasketballPlayers(lines));
        } else if (HANDBALL.name().equals(gameName)) {
            return handballService.getCalculatedPlayers(handballService.getHandballPlayers(lines));
        }
        throw new RuntimeException("Wrong game name");
    }

    private int getPlayerOrderByNickname(String nickname, List<Player> players) {
        for (int i = 0; i < players.size(); i++)
            if(players.get(i).getNickname().equals(nickname))
                return i;
        return -1;
    }

    private Player getMVP(List<Player> players) throws RuntimeException {
        final Optional<Player> mvp = players.stream().max(Comparator.comparing(Player::getRating));
        if (mvp.isPresent())
            return mvp.get();
        throw new RuntimeException("Can't get player with highest rating");
    }
}

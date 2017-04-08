package de.bluesharp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.stream.Stream;

@Data
@Slf4j
class Game {
    private Player[] players;

    Game(short playerCount) {
        log.info("Creating game with {} players", playerCount);

        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("Player " + i);
        }
    }

    void play() {
        for (Player p : players) {
            p.play();
        }

        Stream.of(players)
                .sorted(Comparator.comparingInt(Player::getPoints))
                .forEach(player -> log.info("Player {} achieved {} points", player.getName(), player.getPoints()));
    }
}

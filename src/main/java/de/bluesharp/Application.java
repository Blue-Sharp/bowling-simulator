package de.bluesharp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    private static final short PLAYER_COUNT = Short.MAX_VALUE;


    public static void main(String[] args) throws Exception {
        log.info("Bowling game started");

        Game game = new Game(PLAYER_COUNT);
        game.play();

        log.info("Bowling game finished");
    }
}
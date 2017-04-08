package de.bluesharp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Data
@Slf4j
class Player {
    private String name;
    private Frame[] frames = new Frame[10];
    private int points = 0;

    Player(String name) {
        this.name = name;
        log.info("Welcome to the game player: {}", name);

        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Frame(i);
        }
    }

    void play() {
        for (Frame frame : frames) {
            bowl(frame);
        }
        points = calculatePoints();
    }


    private void bowl(Frame frame) {
        int pins;

        // Frames 1 - 9
        if (frame.getCount() != 9) {
            // First bowling turn
            pins = bowlFirstTurn(frame);
            // Second bowling turn, is only required if the first turn was no Strike.
            if (pins <= 10) {
                bowlSecondTurn(frame);
            }
        }

        // Frame 10
        else {
            // First bowling turn
            pins = bowlFirstTurn(frame);
            // Second bowling turn, is only required if the first turn was no Strike.
            if (pins == 10) {
                pins = bowlSecondTurn(frame);
            }
            // Extra turn because of Strike in turn 1 and turn 2
            if (pins == 10) {
                bowlThirdTurn(frame);
            }
        }

        int totalPoints = getTotalPoints(frame);
        log.info("Player {} finished frame {} and knocked pins {}", name, frame.getCount(), totalPoints);
    }

    private int bowlFirstTurn(Frame frame) {
        // First bowling turn
        int pins;
        frame.increaseTurn();
        pins = new Random().nextInt(11);

        int[] tmp = {pins};
        frame.setResult(tmp);


        log.info("Player {} knocked {} pins in frame {} turn {}", name, pins, frame.getCount(), frame.getTurn());
        return pins;
    }

    private int bowlSecondTurn(Frame frame) {
        // Second bowling turn, is only required if the first turn was no Strike.
        int pins;
        frame.increaseTurn();
        pins = new Random().nextInt((10 - frame.getResult()[0]) + 1);

        int[] tmp = {frame.getResult()[0], pins};
        frame.setResult(tmp);

        log.info("Player {} knocked {} pins in frame {} turn {}", name, pins, frame.getCount(), frame.getTurn());
        return pins;
    }

    private void bowlThirdTurn(Frame frame) {
        // Extra turn because of Strike in turn 1 and turn 2
        int pins;
        frame.increaseTurn();
        pins = new Random().nextInt(11);

        int[] tmp = {frame.getResult()[0], frame.getResult()[1], pins};
        frame.setResult(tmp);

        log.info("Player {} knocked {} pins in frame {} turn {}", name, pins, frame.getCount(), frame.getTurn());
    }


    private int getTotalPoints(Frame frame) {
        int result = 0;
        for (int i : frame.getResult()) {
            result += i;
        }
        return result;
    }

    private int calculatePoints() {
        int result = 0;
        for (int i = 0; i < frames.length; i++) {
            Frame f = frames[i];
            for (int points : f.getResult()) {
                result += points;
            }
            // TODO RK implement Bowling rules VooDoo
            // If Strike
            if (f.isStrike()) {
                if (i == 9) {
                    // Strike in the 10th frame 1rst turn
                    result += frames[i].getResult()[1];
                    // Strike in the 10th frame 2nd turn
                    if (frames[i].getResult().length == 3) {
                        result += frames[i].getResult()[2];
                    }
                } else if (i == 8) {
                    // What happens if an strike happens in the 9th frame and no strike happens ins the 10th frame?
                    // In this case you can not add the result of the 2nd turn in the 10th frame
                    // Also Strike in the 9th frame
                    result += frames[i + 1].getResult()[0];
                    // Also Strike in the 10th frame 2nd turn
                    if (frames[i].getResult().length == 2) {
                        result += frames[i].getResult()[1];
                    }

                } else {
                    // What happens if frames[i + 1].getResult()[0] was also a Strike?
                    if (frames[i + 1].getResult()[0] == 10) {
                        result += frames[i + 2].getResult()[0];
                    } else {
                        result += frames[i + 1].getResult()[1];
                    }
                    // default use case
                    result += frames[i + 1].getResult()[0];
                }

            }
            // If Spare
            if (f.isSpare()) {
                // Default use case
                result += frames[i + 1].getResult()[0];
            }
        }
        return result;
    }
}

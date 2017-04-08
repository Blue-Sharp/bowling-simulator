package de.bluesharp;

import lombok.Data;

@Data
class Frame {
    private int count;
    private int turn = 0;
    private int[] result;

    Frame(int count) {
        this.count = count;
    }

    void increaseTurn() {
        this.turn++;
    }

    boolean isStrike() {
        return result[0] == 10;
    }

    boolean isSpare() {
        return count != 9 && (result[0] + result[1] == 10);
    }
}

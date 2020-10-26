package com.degiro.test;

import java.util.Objects;

public class State {
    int x;
    int y;
    int vx;
    int vy;
    int move;

    public State(int x, int y, int vx, int vy, int move) {
        this.x = x;
        this.y = y;
        this.vx= vx;
        this.vy = vy;
        this.move = move;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXSpeed() {
        return vx;
    }

    public int getYSpeed() {
        return vy;
    }

    public int getMoves() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return x == state.x &&
                y == state.y &&
                vx == state.vx &&
                vy == state.vy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, vx, vy);
    }
}

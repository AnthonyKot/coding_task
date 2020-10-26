package com.degiro.test;

import java.util.*;

public class Maze {
    // x coordinate
    private int length;
    // y coordinate
    private int width;

    private Type[][] fields;
    private Set<State> phaseSpace = new HashSet<>();
    private LinkedHashSet<State> toVisit = new LinkedHashSet<>();

    public Maze(int length, int width, int[][] obstacles, int[] start, int[] finish) {
        this.length = length;
        this.width = width;

        fields = new Type[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = Type.EMPTY;
            }
        }
        for (int[] obstacle : obstacles) {
            int x1 = obstacle[0];
            int x2 = obstacle[1];
            int y1 = obstacle[2];
            int y2 = obstacle[3];
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    fields[i][j] = Type.OBSTACLE;
                }
            }
        }
        fields[start[0]][start[1]] = Type.START;
        fields[finish[0]][finish[1]] = Type.FINISH;
    }

    public int findHops(int[] start, int[] speed) {
        // DFS !
        List<State> finish = new ArrayList<>();
        toVisit.addAll(possibleMoves(start, speed, 0));
        Queue<State> queue = new LinkedList<>();
        while (!toVisit.isEmpty()) {
                State nextState = toVisit.iterator().next();
                toVisit.remove(nextState);
                if (phaseSpace.contains(nextState)) {
                    continue;
                }
                Type cell = fields[nextState.getX()][nextState.getY()];
                if (cell == Type.OBSTACLE) {
                    phaseSpace.add(nextState);
                    continue;
                }
                if (cell == Type.FINISH) {
                    finish.add(nextState);
                }
                phaseSpace.add(nextState);
                start = new int[] {nextState.getX(), nextState.getY()};
                speed = new int[] {nextState.getXSpeed(), nextState.getYSpeed()};
                toVisit.addAll(possibleMoves(start, speed, nextState.getMoves()));
        }
        if (finish.isEmpty()) {
            return -1;
        }
        return finish.stream().mapToInt(st -> st.getMoves()).min().getAsInt();
    }

    public void printMe() {
        for (int j = width - 1; j >= 0; j--) {
            for (int i = 0; i < length; i++) {
                Type field = fields[i][j];
                switch (field) {
                    case EMPTY:
                        System.out.print(".");
                        break;
                    case OBSTACLE:
                        System.out.print("x");
                        break;
                    case START:
                        System.out.print("S");
                        break;
                    case FINISH:
                        System.out.print("F");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private Set<State> possibleMoves(int[] from, int[] speed, int move) {
        // acceleration gives 9 options for speed
        Set<State> states = new HashSet<>(10);
        for (int[] possibleSpeed : possibleSpeed(speed)) {
            int movedToX = from[0] + possibleSpeed[0];
            int movedToY = from[1] + possibleSpeed[1];
            if (movedToX >= 0 && movedToX < length && movedToY >= 0 && movedToY < width) {
                State state = new State(
                        movedToX,
                        movedToY,
                        possibleSpeed[0],
                        possibleSpeed[1],
                        move + 1);
                states.add(state);
            }
        }
        return states;
    }

    private Set<int[]> possibleSpeed(int[] speed) {
        int[][] speeds = new int[9][2];
        // 1
        speeds[0][0] = speed[0] - 1;
        speeds[0][1] = speed[1] - 1;
        // 2
        speeds[1][0] = speed[0];
        speeds[1][1] = speed[1] - 1;
        // 3
        speeds[2][0] = speed[0] - 1;
        speeds[2][1] = speed[1];
        // 4
        speeds[3][0] = speed[0] - 1;
        speeds[3][1] = speed[1] + 1;
        // 5
        speeds[4][0] = speed[0] + 1;
        speeds[4][1] = speed[1] - 1;
        // 6
        speeds[5][0] = speed[0];
        speeds[5][1] = speed[1];
        // 7
        speeds[6][0] = speed[0];
        speeds[6][1] = speed[1] + 1;
        // 8
        speeds[7][0] = speed[0] + 1;
        speeds[7][1] = speed[1];
        // 9
        speeds[8][0] = speed[0] + 1;
        speeds[8][1] = speed[1] + 1;

        Set<int[]> allowedSpeeds = new HashSet<>(10);
        for (int[] possibleSpeed : speeds) {
            if (possibleSpeed[0] < 4 && possibleSpeed[0] > -4 && possibleSpeed[1] < 4 && possibleSpeed[1] > -4) {
                allowedSpeeds.add(possibleSpeed);
            }
        }

        return allowedSpeeds;
    }
}

package com.degiro.test;

public class Main {

    public static void main(String[] args) {
        int[][] task1 = new int[][]{{1}, {5, 5}, {4, 0, 4, 4}, {1}, {1, 4, 2, 3}};
        int answer = 7;
        int[] startPoint = new int[]{task1[2][0], task1[2][1]};
        int[] endPoint = new int[]{task1[2][2], task1[2][3]};
        int[] startSpeed = new int[]{0 ,0};
        Maze maze = new Maze(
                task1[1][0],
                task1[1][1],
                new int[][]{task1[4]},
                startPoint,
                endPoint
                );
        maze.printMe();
        System.out.println(maze.findHops(startPoint, startSpeed));
    }
}

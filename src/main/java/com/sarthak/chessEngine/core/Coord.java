package com.sarthak.chessEngine.core;

public class Coord {
    public static int getCoord(int rank, int file) { return rank * 8 + file; }
    
    public static int rank(int square) { return square / 8; }
    
    public static int file(int square) { return square % 8; }
    
    public static boolean isValidSquare(int square) {
        int r = rank(square), f = file(square);
        return (r >= 0 && r < 8 && f >= 0 && f < 8);
    }
    
    public static boolean sameRank(int square1, int square2) { return rank(square1) == rank(square2); }
    
    public static boolean sameFile(int square1, int square2) { return file(square1) == file(square2); }
    
}

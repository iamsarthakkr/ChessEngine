package com.sarthak.chessEngine.utility;

public class BoardHelper {
    public static int getCoord(int rank, int file) { return rank * 8 + file; }
    
    public static int rank(int square) { return square / 8; }
    
    public static int file(int square) { return square % 8; }
    
    public static boolean isValidSquare(int square) {
        int r = rank(square), f = file(square);
        return (r >= 0 && r < 8 && f >= 0 && f < 8);
    }
    
    public static boolean sameRank(int square1, int square2) { return rank(square1) == rank(square2); }
    
    public static boolean sameFile(int square1, int square2) { return file(square1) == file(square2); }
    
    public static int fromString(String square) {
        if(square.length() != 2) throw new IllegalArgumentException("Invalid square " + square);
        char file = square.charAt(0), rank = square.charAt(1);
        if(file < 'a' || file > 'h' || rank < '1' || rank > '8') throw new IllegalArgumentException("Invalid square " + square);
        
        return getCoord(rank - '1', file - 'a');
    }
    
}

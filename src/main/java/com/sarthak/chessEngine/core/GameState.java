package com.sarthak.chessEngine.core;

public class GameState {
    private static final int WhiteKingSideCastlingRightMask = 0b0001;
    private static final int WhiteQueenSideCastlingRightMask = 0b0010;
    private static final int BlackKingSideCastlingRightMask = 0b0100;
    private static final int BlackQueenSideCastlingRightMask = 0b1000;
    
    private static final int ClearWhiteKingSideCastlingRightMask = 0b1110;
    private static final int ClearWhiteQueenSideCastlingRightMask = 0b1101;
    private static final int ClearBlackKingSideCastlingRightMask = 0b1011;
    private static final int ClearBlackQueenSideCastlingRightMask = 0b0111;
    
    public int enPassantSquare;
    public int castlingRights;
    public int halfMoveClock;
    public int fullMoveCount;
    
    public GameState() {
        this.enPassantSquare = -1;
        this.castlingRights = WhiteKingSideCastlingRightMask |
            WhiteQueenSideCastlingRightMask |
            BlackKingSideCastlingRightMask |
            BlackQueenSideCastlingRightMask;
        this.halfMoveClock = 0;
        this.fullMoveCount = 0;
    }
    
    public GameState(int enPassantSquare, int castlingRights, int halfMoveClock, int fullMoveCount) {
        this.enPassantSquare = enPassantSquare;
        this.castlingRights = castlingRights;
        this.halfMoveClock = halfMoveClock;
        this.fullMoveCount = fullMoveCount;
    }
    
    public boolean canCastleKingSide(int side) {
        int mask = side == Piece.White ? WhiteKingSideCastlingRightMask : BlackKingSideCastlingRightMask;
        return (castlingRights & mask) != 0;
    }
    
    public boolean canCastleQueenSide(int side) {
        int mask = side == Piece.White ? WhiteQueenSideCastlingRightMask : BlackQueenSideCastlingRightMask;
        return (castlingRights & mask) != 0;
    }
    
    public void clearKingSideCastlingRights(int side) {
        int mask = side == Piece.White ? ClearWhiteKingSideCastlingRightMask : ClearBlackKingSideCastlingRightMask;
        this.castlingRights &= mask;
    }
    
    public void clearQueenSideCastlingRights(int side) {
        int mask = side == Piece.White ? ClearWhiteQueenSideCastlingRightMask : ClearBlackQueenSideCastlingRightMask;
        this.castlingRights &= mask;
    }
}


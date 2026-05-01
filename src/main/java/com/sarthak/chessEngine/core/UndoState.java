package com.sarthak.chessEngine.core;

public class UndoState {
    public final int previousEnPassantSquare;
    public final int previousCastlingRights;
    public final int previousCapturedPiece;
    public final int previousHalfMoveClock;
    
    public UndoState(int previousEnPassantSquare, int previousCastlingRights, int previousCapturedPiece, int previousHalfMoveClock) {
        this.previousEnPassantSquare = previousEnPassantSquare;
        this.previousCastlingRights = previousCastlingRights;
        this.previousCapturedPiece = previousCapturedPiece;
        this.previousHalfMoveClock = previousHalfMoveClock;
    }
}

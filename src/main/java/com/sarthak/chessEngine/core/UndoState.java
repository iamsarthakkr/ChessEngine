package com.sarthak.chessEngine.core;

public class UndoState {
    public final int previousEnPassantSquare;
    public final int previousCastlingRights;
    public final int previousCapturedPiece;
    public final int previousHalfMoveClock;
    public final int previousFullMoveNumber;
    
    public UndoState(
        int previousEnPassantSquare,
        int previousCastlingRights,
        int previousCapturedPiece,
        int previousHalfMoveClock,
        int previousFullMoveNumber
    ) {
        this.previousEnPassantSquare = previousEnPassantSquare;
        this.previousCastlingRights = previousCastlingRights;
        this.previousCapturedPiece = previousCapturedPiece;
        this.previousHalfMoveClock = previousHalfMoveClock;
        this.previousFullMoveNumber = previousFullMoveNumber;
    }
}

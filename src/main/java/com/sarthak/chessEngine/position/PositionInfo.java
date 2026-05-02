package com.sarthak.chessEngine.position;

import com.sarthak.chessEngine.core.GameState;

import java.util.Arrays;

public class PositionInfo {
    private final int[] squares;
    private final int sideToMove;
    private final int castlingRights;
    private final int enPassantSquare;
    private final int halfMoveClock;
    private final int fullMoveNumber;
    
    private PositionInfo(Builder builder) {
        this.squares = Arrays.copyOf(builder.squares, 64);
        this.sideToMove = builder.sideToMove;
        this.castlingRights = builder.castlingRights;
        this.enPassantSquare = builder.enPassantSquare;
        this.halfMoveClock = builder.halfMoveClock;
        this.fullMoveNumber = builder.fullMoveNumber;
    }
    
    public static Builder builder() { return new Builder(); }
    
    public int[] getSquaresCopy() { return Arrays.copyOf(this.squares, 64); }
    public int getSideToMove() { return sideToMove; }
    public int getCastlingRights() { return castlingRights; }
    public int getEnPassantSquare() { return enPassantSquare; }
    public int getHalfMoveClock() { return halfMoveClock; }
    public int getFullMoveNumber() { return fullMoveNumber; }
    
    public int getPieceAt(int sq) {
        return squares[sq];
    }
    
    public static class Builder {
        private int[] squares;
        private int sideToMove;
        private int castlingRights = 0;
        private int enPassantSquare = -1;
        private int halfMoveClock = 0;
        private int fullMoveNumber = 1;
        
        public Builder squares(int[] squares) {
            this.squares = squares;
            return this;
        }
        public Builder sideToMove(int sideToMove) {
            this.sideToMove = sideToMove;
            return this;
        }
        public Builder castlingRights(int castlingRights) {
            this.castlingRights = castlingRights;
            return this;
        }
        public Builder enPassantSquare(int enPassantSquare) {
            this.enPassantSquare = enPassantSquare;
            return this;
        }
        public Builder halfMoveClock(int halfMoveClock) {
            this.halfMoveClock = halfMoveClock;
            return this;
        }
        public Builder fullMoveNumber(int fullMoveNumber) {
            this.fullMoveNumber = fullMoveNumber;
            return this;
        }
        public PositionInfo build() {
            if(null == squares || squares.length != 64) throw new IllegalArgumentException("Squares must be length 64");
            if (sideToMove != com.sarthak.chessEngine.core.Piece.White &&
                sideToMove != com.sarthak.chessEngine.core.Piece.Black) { throw new IllegalStateException("invalid sideToMove"); }
            
            // enPassantSquare can be -1 or [0..63]
            if (enPassantSquare < -1 || enPassantSquare >= 64) { throw new IllegalStateException("invalid enPassantSquare"); }
            
            // valid castling rights
            if (!GameState.validCastlingRights(castlingRights)) { throw new IllegalStateException("invalid castlingRights"); }
            
            if (halfMoveClock < 0) { throw new IllegalStateException("invalid halfMoveClock"); }
            if (fullMoveNumber < 1) { throw new IllegalStateException("invalid fullMoveNumber"); }
            
            return new PositionInfo(this);
        }
    }
}

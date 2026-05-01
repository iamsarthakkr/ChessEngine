package com.sarthak.chessEngine.core;

public class Move {
    // Move flags
    public static int NoFlag = 0b000;
    public static int EnPassantCaptureFlag = 0b001;
    public static int CastleFlag = 0b010;
    public static int PawnTwoMoveFlag = 0b011;
    
    public static int PromoteToQueenFlag = 0b100;
    public static int PromoteToRookFlag = 0b101;
    public static int PromoteToBishopFlag = 0b110;
    public static int PromoteToKnightFlag = 0b111;
    
    public final int fromSquare;
    public final int toSquare;
    public final int moveFlag;
    public final int capturedPieceType;
    
    public Move(int fromSquare, int toSquare, int moveFlag) { this(fromSquare, toSquare, moveFlag, Piece.None); }
    public Move(int fromSquare, int toSquare) { this(fromSquare, toSquare, NoFlag, Piece.None); }
    public Move(int fromSquare, int toSquare, int moveFlag, int capturedPieceType) {
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.moveFlag = moveFlag;
        this.capturedPieceType = capturedPieceType;
    }
}

package com.sarthak.chessEngine.core;

public class Move {
    // Move flags
    public static final int NoFlag = 0b000;
    public static final int EnPassantCaptureFlag = 0b001;
    public static final int CastleFlag = 0b010;
    public static final int PawnTwoMoveFlag = 0b011;
    
    public static final int PromoteToKnightFlag = 0b100;
    public static final int PromoteToBishopFlag = 0b101;
    public static final int PromoteToRookFlag = 0b110;
    public static final int PromoteToQueenFlag = 0b111;
    
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
    
    public boolean isEnPassantCapture() { return moveFlag == EnPassantCaptureFlag; }
    public boolean isCastle() { return moveFlag == CastleFlag; }
    public boolean isPawnTwoUp() { return moveFlag == PawnTwoMoveFlag; }
    public boolean isPromotion() { return moveFlag >= PromoteToKnightFlag && moveFlag <= PromoteToQueenFlag; }
    public int getPromotionPieceType() {
        return switch (moveFlag) {
            case PromoteToKnightFlag -> Piece.Knight;
            case PromoteToBishopFlag -> Piece.Bishop;
            case PromoteToRookFlag -> Piece.Rook;
            case PromoteToQueenFlag -> Piece.Queen;
            default -> Piece.None;
        };
    }
}


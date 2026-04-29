package com.sarthak.chessEngine.core;

public class Piece {
    public static final int None = 0;
    public static final int Pawn = 1;
    public static final int Knight = 2;
    public static final int Bishop = 3;
    public static final int Rook = 4;
    public static final int Queen = 5;
    public static final int King = 6;
    
    public static final int White = 1;
    public static final int Black = -1;
    
    // Pieces
    public static final int WhitePawn = 1;
    public static final int WhiteKnight = 2;
    public static final int WhiteBishop = 3;
    public static final int WhiteRook = 4;
    public static final int WhiteQueen = 5;
    public static final int WhiteKing = 6;
    
    public static final int BlackPawn = -1;
    public static final int BlackKnight = -2;
    public static final int BlackBishop = -3;
    public static final int BlackRook = -4;
    public static final int BlackQueen = -5;
    public static final int BlackKing = -6;
    
    public static boolean isColor(int piece, int color) { return piece != None && piece * color > 0; }
    
    public static boolean isWhite(int piece) { return isColor(piece, White); }
    public static boolean isBlack(int piece) { return isColor(piece, Black); }
    
    public static int pieceType(int piece) { return Math.abs(piece); }
    public static int pieceColor(int piece) { return piece == None ? None : piece > 0 ? White : Black; }
    
    public static int makePiece(int pieceType, int color) { return pieceType * color; }
    public static int makePiece(int pieceType, boolean color) { return makePiece(pieceType, color ? White : Black); }
    
    public static char getSymbol(int piece) {
        char symbol = switch (pieceType(piece)) {
            case Pawn -> 'P';
            case Bishop -> 'B';
            case Knight -> 'N';
            case Rook -> 'R';
            case Queen -> 'Q';
            case King -> 'K';
            case None -> '.';
            default -> throw new IllegalArgumentException("Invalid piece " + piece);
        };
        return pieceColor(piece) == Black ? Character.toLowerCase(symbol) : symbol;
    }
    
    public static int getPiece(char symbol) {
        int pieceType = switch (Character.toUpperCase(symbol)) {
            case 'P' -> Pawn;
            case 'B' -> Bishop;
            case 'N' -> Knight;
            case 'R' -> Rook;
            case 'Q' -> Queen;
            case 'K' -> King;
            case '.' -> None;
            default -> throw new IllegalArgumentException("Invalid symbol " + symbol);
        };
        return makePiece(pieceType, Character.isUpperCase(symbol));
    }
}

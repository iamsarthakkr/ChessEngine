package com.sarthak.chessEngine.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    
    @Test
    void isColor_and_isWhite_isBlack() {
        assertTrue(Piece.isColor(Piece.WhitePawn, Piece.White));
        assertFalse(Piece.isColor(Piece.WhitePawn, Piece.Black));
        
        assertTrue(Piece.isWhite(Piece.WhiteKnight));
        assertFalse(Piece.isWhite(Piece.BlackKnight));
        
        assertTrue(Piece.isBlack(Piece.BlackBishop));
        assertFalse(Piece.isBlack(Piece.WhiteBishop));
        
       // None case
        assertFalse(Piece.isColor(Piece.None, Piece.White));
        assertFalse(Piece.isColor(Piece.None, Piece.Black));
    }
    
    @Test
    void pieceType_and_pieceColor() {
        assertEquals(Piece.Pawn, Piece.pieceType(Piece.WhitePawn));
        assertEquals(Piece.Knight, Piece.pieceType(Piece.BlackKnight));
        assertEquals(Piece.None, Piece.pieceType(Piece.None));
        
        assertEquals(Piece.White, Piece.pieceColor(Piece.WhiteQueen));
        assertEquals(Piece.Black, Piece.pieceColor(Piece.BlackQueen));
        assertEquals(Piece.None, Piece.pieceColor(Piece.None));
    }
    
    @Test
    void makePiece_overloads() {
        assertEquals(Piece.WhiteRook, Piece.makePiece(Piece.Rook, Piece.White));
        assertEquals(Piece.BlackRook, Piece.makePiece(Piece.Rook, Piece.Black));
        
        assertEquals(Piece.WhiteBishop, Piece.makePiece(Piece.Bishop, true));
        assertEquals(Piece.BlackBishop, Piece.makePiece(Piece.Bishop, false));
    }
    
    @ParameterizedTest(name = "{0} -> symbol {1}")
    @CsvSource({
        "WhitePawn, P",
        "WhiteKnight, N",
        "WhiteBishop, B",
        "WhiteRook, R",
        "WhiteQueen, Q",
        "WhiteKing, K",
        "None, ."
    })
    void getSymbol_for_white_and_none(String pieceConstName, char expected) throws Exception {
        int piece = (int) Piece.class.getField(pieceConstName).get(null);
        assertEquals(expected, Piece.getSymbol(piece));
    }
    
    @ParameterizedTest(name = "{0} -> symbol {1}")
    @CsvSource({
        "BlackPawn, p",
        "BlackKnight, n",
        "BlackBishop, b",
        "BlackRook, r",
        "BlackQueen, q",
        "BlackKing, k"
    })
    void getSymbol_for_black(String pieceConstName, char expected) throws Exception {
        int piece = (int) Piece.class.getField(pieceConstName).get(null);
        assertEquals(expected, Piece.getSymbol(piece));
    }
    
    @Test
    void getSymbol_invalidPiece_throws() {
        assertThrows(IllegalArgumentException.class, () -> Piece.getSymbol(7));
        assertThrows(IllegalArgumentException.class, () -> Piece.getSymbol(-7));
        // also test out-of-range like 100
        assertThrows(IllegalArgumentException.class, () -> Piece.getSymbol(100));
    }
    
    @ParameterizedTest(name = "{0} -> piece {1}")
    @CsvSource({
        "P, 1",
        "N, 2",
        "B, 3",
        "R, 4",
        "Q, 5",
        "K, 6",
        "p, -1",
        "n, -2",
        "b, -3",
        "r, -4",
        "q, -5",
        "k, -6",
        "., 0"
    })
    void getPiece_and_roundtrip(String symbolStr, int expectedPiece) {
        char symbol = symbolStr.charAt(0);
        int piece = Piece.getPiece(symbol);
        assertEquals(expectedPiece, piece);
        
        // round-trip: getSymbol(getPiece(symbol)) should return the same symbol
        assertEquals(symbol, Piece.getSymbol(piece));
    }
    
    @Test
    void getPiece_invalidSymbol_throws() {
        assertThrows(IllegalArgumentException.class, () -> Piece.getPiece('x'));
        assertThrows(IllegalArgumentException.class, () -> Piece.getPiece('1'));
        assertThrows(IllegalArgumentException.class, () -> Piece.getPiece(' '));
    }
}
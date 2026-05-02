package com.sarthak.chessEngine.io.fen;

import com.sarthak.chessEngine.core.GameState;
import com.sarthak.chessEngine.core.Piece;
import com.sarthak.chessEngine.position.PositionInfo;
import com.sarthak.chessEngine.utility.BoardHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FenParser {
    public static PositionInfo parse(final String fenString) {
        PositionInfo.Builder builder = PositionInfo.builder();
        // 0 -> position, 1 -> side, 2 -> castling, 3 -> enPassant sq, 4 -> half move, 5 -> full move
        String[] fenParts = fenString.split(" ");
        if(fenParts.length != 6) {
            throw new IllegalArgumentException("Invalid fen string " + fenString);
        }
        
        parseSquares(builder, fenParts[0]);
        parseSideToMove(builder, fenParts[1]);
        parseCastlingRights(builder, fenParts[2]);
        parseEnPassantSquare(builder, fenParts[3]);
        parseMoveClocks(builder, fenParts[4], fenParts[5]);
        
        return builder.build();
    }
    
    private static void parseSquares(PositionInfo.Builder builder, String fenSquaresPart) {
        int[] squares = new int[64];
        Arrays.fill(squares, Piece.None);
        
        List<String> boardParts = new ArrayList<>(Arrays.stream(fenSquaresPart.split("/")).toList());
        Collections.reverse(boardParts);
        
        if(boardParts.size() != 8) throw new IllegalArgumentException("Invalid fen string " + fenSquaresPart);
        
        // setup board
        int boardIndex = 0;
        for(final String rankPart: boardParts) {
            int count = 0;
            for(char v: rankPart.toCharArray()) {
                if(v >= '1' && v <= '9') {
                    int emptySquares = v - '0';
                    count += emptySquares;
                    boardIndex += emptySquares;
                    continue;
                }
                try {
                    squares[boardIndex] = Piece.getPiece(v);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Invalid character for piece " + v);
                }
                boardIndex++;
                count += 1;
            }
            if(count != 8) {
                throw new IllegalArgumentException("Invalid fen board layout " + rankPart);
            }
        }
        if(boardIndex != 64) throw new IllegalArgumentException("Invalid fen board layout " + fenSquaresPart);
        builder.squares(squares);
    }
    
    private static void parseSideToMove(PositionInfo.Builder builder, String fenSideToMovePart) {
        int sideToMove = switch (fenSideToMovePart) {
            case "w" -> Piece.White;
            case "b" -> Piece.Black;
            default -> throw new IllegalArgumentException("Invalid side to move " + fenSideToMovePart);
        };
        builder.sideToMove(sideToMove);
    }
    
    private static void parseCastlingRights(PositionInfo.Builder builder, String fenCastlingRightsPart) {
        boolean whiteCastleKingSide = fenCastlingRightsPart.contains("K");
        boolean whiteCastleQueenSide = fenCastlingRightsPart.contains("Q");
        boolean blackCastleKingSide = fenCastlingRightsPart.contains("k");
        boolean blackCastleQueenSide = fenCastlingRightsPart.contains("q");
        
        builder.castlingRights(GameState.createCastlingRights(whiteCastleKingSide, whiteCastleQueenSide, blackCastleKingSide, blackCastleQueenSide));
    }
    
    private static void parseEnPassantSquare(PositionInfo.Builder builder, String fenEnPassantSquarePart) {
        if(fenEnPassantSquarePart.equals("-")) {
            builder.enPassantSquare(-1);
            return;
        }
        try {
            builder.enPassantSquare(BoardHelper.fromString(fenEnPassantSquarePart));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid enPassant square " + fenEnPassantSquarePart);
        }
    }
    
    private static void parseMoveClocks(PositionInfo.Builder builder, String fenHalfMoveClockPart, String fenFullMoveNumberPart) {
        builder.halfMoveClock(Integer.parseInt(fenHalfMoveClockPart));
        builder.fullMoveNumber(Integer.parseInt(fenFullMoveNumberPart));
    }
}

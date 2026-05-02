package com.sarthak.chessEngine.core;

import com.sarthak.chessEngine.io.fen.FenParser;
import com.sarthak.chessEngine.position.PositionInfo;
import com.sarthak.chessEngine.utility.BoardHelper;

import java.util.Arrays;
import java.util.Stack;

public class Board {
    private int[] board;
    private PositionInfo startingPositionInfo;
    
    private int sideToMove;
    private GameState gameState;
    private final Stack<UndoState> undoStates;
    private final Stack<Move> allGameMoves;
    
    private Board() {
        board = new int[64];
        this.gameState = new GameState();
        this.undoStates = new Stack<>();
        this.allGameMoves = new Stack<>();
    }
    
    public int[] getBoard() { return Arrays.copyOf(this.board, 64); }
    public PositionInfo getStartingPositionInfo() { return this.startingPositionInfo; }
    
    public void makeMove(Move move) {
        int movePiece = board[move.fromSquare];
        int movePieceType = Piece.pieceType(movePiece);
        
        int newEnPassantSquare = -1;
        int newCastlingRights = gameState.castlingRights;
        int newHalfMoveClock = gameState.halfMoveClock;
        int newFullMoveNumber = gameState.fullMoveNumber;
        
        // move piece
        board[move.fromSquare] = Piece.None;
        board[move.toSquare] = movePiece;
        
        // handle capture
        if(move.capturedPieceType != Piece.None) {
            int captureSquare = move.toSquare;
            if(move.isEnPassantCapture()) {
                captureSquare += (sideToMove == Piece.White ? -BoardHelper.RankOffset : BoardHelper.RankOffset);
                board[captureSquare] = Piece.None;
            }
        }
        
        // handle king move
        if(movePieceType == Piece.King) {
            // remove all castling rights for this side
            newCastlingRights = GameState.clearCastlingRight(newCastlingRights, sideToMove);
            
            // handle castling
            if(move.isCastle()) {
                boolean kingSide = move.toSquare == BoardHelper.g1 || move.toSquare == BoardHelper.g8;
                int rookPiece = Piece.makePiece(Piece.Rook, sideToMove);
                int rookFromSquare = move.toSquare + (kingSide ? BoardHelper.FileOffset : 2 * BoardHelper.FileOffset);
                int rookToSquare = move.toSquare + (kingSide ? -BoardHelper.FileOffset : BoardHelper.FileOffset);
                
                board[rookFromSquare] = Piece.None;
                board[rookToSquare] = rookPiece;
            }
        }
        
        // handle promotion
        if(move.isPromotion()) {
            int promotionPieceType = move.getPromotionPieceType();
            int promotionPiece = Piece.makePiece(promotionPieceType, sideToMove);
            
            board[move.toSquare] = promotionPiece;
        }
        
        // update castling rights
        if(newCastlingRights != 0) {
            if(move.fromSquare == BoardHelper.h1 || move.toSquare == BoardHelper.h1) {
                newCastlingRights = GameState.clearKingSideCastlingRights(newCastlingRights, Piece.White);
            }
            if(move.fromSquare == BoardHelper.h8 || move.toSquare == BoardHelper.h8) {
                newCastlingRights = GameState.clearKingSideCastlingRights(newCastlingRights, Piece.Black);
            }
            if(move.fromSquare == BoardHelper.a1 || move.toSquare == BoardHelper.a1) {
                newCastlingRights = GameState.clearQueenSideCastlingRights(newCastlingRights, Piece.White);
            }
            if(move.fromSquare == BoardHelper.a8 || move.toSquare == BoardHelper.a8) {
                newCastlingRights = GameState.clearQueenSideCastlingRights(newCastlingRights, Piece.Black);
            }
        }
        
        // update new en passant square
        if(move.isPawnTwoUp()) {
            newEnPassantSquare = move.toSquare + (sideToMove == Piece.White ? -BoardHelper.RankOffset : BoardHelper.RankOffset);
        }
        
        // update game states
        newHalfMoveClock += 1;
        if(movePieceType == Piece.Pawn || move.capturedPieceType != Piece.None) { newHalfMoveClock = 0; }
        if(sideToMove == Piece.Black) { newFullMoveNumber += 1; }
        sideToMove = sideToMove == Piece.White ? Piece.Black : Piece.White;
        
        GameState newGameState = new GameState(newEnPassantSquare, newCastlingRights, newHalfMoveClock, newFullMoveNumber);
        // push to undo state
        undoStates.push(new UndoState(gameState.enPassantSquare, gameState.castlingRights, move.capturedPieceType, gameState.halfMoveClock, gameState.fullMoveNumber));
        gameState = newGameState;
        allGameMoves.push(move);
    }
    
    public void unMakeMove() {
    
    }
    
    private void loadPosition(String fenString) {
        PositionInfo startingPositionInfo = FenParser.parse(fenString);
        loadPosition(startingPositionInfo);
    }
    
    private void loadPosition(Board board) {
        this.loadPosition(board.getStartingPositionInfo());
        
        for(Move move: board.allGameMoves) {
            this.makeMove(move);
        }
    }
    
    private void loadPosition(PositionInfo positionInfo) {
        this.board = positionInfo.getSquaresCopy();
        this.sideToMove = positionInfo.getSideToMove();
        
        gameState.castlingRights = positionInfo.getCastlingRights();
        gameState.enPassantSquare = positionInfo.getEnPassantSquare();
        gameState.halfMoveClock = positionInfo.getHalfMoveClock();
        gameState.fullMoveNumber = positionInfo.getFullMoveNumber();
        
        this.startingPositionInfo = positionInfo;
    }
    
    public static Board createBoard(final String fenStartingPosition) {
        Board board = new Board();
        board.loadPosition(fenStartingPosition);
        return board;
    }
    
    public static Board createBoard(final PositionInfo startingPositionInfo) {
        Board board = new Board();
        board.loadPosition(startingPositionInfo);
        return board;
    }
    
    public static Board createBoard(final Board other) {
        Board board = new Board();
        board.loadPosition(other);
        return board;
    }
    
}

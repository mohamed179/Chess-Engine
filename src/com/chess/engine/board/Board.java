package com.chess.engine.board;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.board.moves.Move;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.BlackPlayer;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Mohamed Kamal
 */
public final class Board {
    
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    
    private Board (final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.enPassantPawn = builder.enPassantPawn;
        whitePieces = calculateActionPieces(gameBoard, Alliance.WHITE);
        blackPieces = calculateActionPieces(gameBoard, Alliance.BLACK);
        
        final Collection<Move> whiteStatnardLegalMoves = calculatedLegalMoves(this.whitePieces);
        final Collection<Move> blackStrndardLegalMoves = calculatedLegalMoves(this.blackPieces);
        
        this.whitePlayer = new WhitePlayer(this, whiteStatnardLegalMoves, blackStrndardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackStrndardLegalMoves, whiteStatnardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(whitePlayer, blackPlayer);
    }
    
    private Collection<Move> calculatedLegalMoves (final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculatedLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
    public Iterable<Move> getAllLegalMoves () {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                                                               this.blackPlayer.getLegalMoves()));
    }
    
    public Collection<Piece> getWhitePieces () {
        return whitePieces;
    }
    
    public Collection<Piece> getBlackPieces () {
        return blackPieces;
    }
    
    public Player getWhitePlayer () {
        return this.whitePlayer;
    }
    
    public Player getBlackPlayer () {
        return this.blackPlayer;
    }
    
    public Player getCurrentPlayer () {
        return currentPlayer;
    }
    
    public Pawn getEnPassantPawn () {
        return this.enPassantPawn;
    }
    
    @Override
    public String toString () {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            builder.append(String.format("%3s", gameBoard.get(i).toString()));
            if ((i + 1 ) % BoardUtils.NUM_TILES_PER_ROW == 0)
                builder.append("\n");
        }
        return builder.toString();
    }
    
    private static Collection<Piece> calculateActionPieces (final List<Tile> gameBoard,
                                                            final Alliance alliance) {
        final List<Piece> actionPieces = new ArrayList();
        for (final Tile tile : gameBoard) {
            if (tile.isOccupated()) {
                Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    actionPieces.add(piece);
                }
            }
        }
        
        return ImmutableList.copyOf(actionPieces);
    }
    
    public  Tile getTile (final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }
    
    private static List<Tile> createGameBoard (Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }
    
    public static final Board createStandardBoard () {
        final Builder builder = new Builder();
        
        //black pieces...
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece(new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        for (int i = 8; i < 16; i++)
            builder.setPiece(new Pawn(i, Alliance.BLACK));
        
        //white pieces...
        for (int i = 48; i < 56; i++)
            builder.setPiece(new Pawn(i, Alliance.WHITE));
        builder.setPiece(new Rook(56, Alliance.WHITE));
        builder.setPiece(new Knight(57, Alliance.WHITE));
        builder.setPiece(new Bishop(58, Alliance.WHITE));
        builder.setPiece(new Queen(59, Alliance.WHITE));
        builder.setPiece(new King(60, Alliance.WHITE));
        builder.setPiece(new Bishop(61, Alliance.WHITE));
        builder.setPiece(new Knight(62, Alliance.WHITE));
        builder.setPiece(new Rook(63, Alliance.WHITE));
        
        //white has the first move...
        builder.setNextMoveMaker(Alliance.WHITE);
        
        return builder.build();
    }
    
    public static final class Builder {
        
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        
        public Builder () {
            boardConfig = new HashMap();
        }
        
        public Builder setPiece (Piece piece) {
            boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        
        public Builder setEnPassantPawn (Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }
        
        public Builder setNextMoveMaker (Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        
        public Board build () {
            return new Board(this);
        }
    }
    
}

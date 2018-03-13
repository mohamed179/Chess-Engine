package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.pieces.Piece;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;

/**
 *
 * @author Mohamed Kamal
 */
public abstract class Move {
    
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;
    protected String moveString;
    
    private static final Move NULL_MOVE = new NullMove();
    
    public Move (final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate,
                 final boolean isFirstMove) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = isFirstMove;
        this.moveString = "";
    }
    
    public Move (final Board board,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }
    
    public int getDestinationCoordinate () {
        return this.destinationCoordinate;
    }
    
    public int getCurrentCoordinate () {
        return this.movedPiece.getPiecePosition();
    }
    
    public Piece getMovedPiece () {
        return this.movedPiece;
    }
    
    public boolean isAttackMove () {
        return false;
    }
    
    public boolean isCastleMove () {
        return false;
    }
    
    public boolean isPawnEnPassantAttackMove () {
        return false;
    }
    
    public Piece getAttackedPiece () {
        return null;
    }
    
    public void setMoveString (final String moveString) {
        this.moveString = moveString;
    }
    
    public String getMoveString () {
        return this.moveString;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof Move)) {
            return false;
        }
        final Move move = (Move) obj;
        return this.getCurrentCoordinate() == move.getCurrentCoordinate() &&
               this.destinationCoordinate == move.destinationCoordinate &&
               this.movedPiece.equals(move);
    }
    
    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    public Board execute () {
        this.moveString = toString();
        final Builder builder = new Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (! this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
    
    public static final class MoveFactory {
        private MoveFactory () {
            throw new RuntimeException("Not installable!");
        }
        
        public static final Move createMove (final Board board,
                                             final int currentCoordinate,
                                             final int destinationCoordinate) {
            for (Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate && 
                    move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
    
}

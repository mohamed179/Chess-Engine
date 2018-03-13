package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

/**
 *
 * @author Mohamed Kamal
 */
class CastleMove extends Move {
    
    protected final Rook castleRook;
    protected final int castleRookStart;
    protected final int castleRookDestination;

    public CastleMove(final Board board,
                      final Piece movedPiece,
                      final int destinationCoordinate,
                      final Rook castleRook,
                      final int castleRookStart,
                      final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate, true);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookDestination = castleRookDestination;
    }
    
    public Rook getCastleRook () {
        return this.castleRook;
    }
    
    public int getCastleRookStart () {
        return this.castleRookStart;
    }
    
    public int getCastleRookDestination () {
        return this.castleRookDestination;
    }
    
    @Override
    public boolean isCastleMove () {
        return true;
    }

    @Override
    public Board execute () {
        this.moveString = toString();
        final Builder builder = new Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (! this.movedPiece.equals(piece) && ! this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // TODO: look at the first move on mormal pieces...
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setPiece(new Rook(this.castleRookDestination, this.board.getCurrentPlayer().getAlliance()));
        builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
    
    @Override
    public int hashCode () {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.castleRook.hashCode();
        result = prime * result + this.castleRookStart;
        result = prime * result + this.castleRookDestination;
        return result;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof CastleMove)) {
            return false;
        }
        final CastleMove castleMove = (CastleMove) obj;
        return super.equals(castleMove) && this.castleRook.equals(castleMove.getCastleRook());
    }
    
}

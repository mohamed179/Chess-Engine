package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Pawn;

/**
 *
 * @author Mohamed Kamal
 */
public class PawnPromotionMove extends Move {
    
    private final Move pawnMove;
    
    public PawnPromotionMove(final Move move) {
        super(move.board, move.movedPiece, move.destinationCoordinate, false);
        this.pawnMove = move;
    }
    
    @Override
    public Piece getAttackedPiece () {
        return this.pawnMove.getAttackedPiece();
    }
    
    @Override
    public boolean isAttackMove () {
        return this.pawnMove.isAttackMove();
    }
    
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof PawnPromotionMove)) {
            return false;
        }
        final PawnPromotionMove pawnPromotionMove = (PawnPromotionMove) obj;
        return this.pawnMove.equals(pawnPromotionMove.pawnMove);
    }
    
    @Override
    public int hashCode () {
        return this.pawnMove.hashCode() + 31 * this.pawnMove.movedPiece.hashCode();
    }
    
    @Override
    public Board execute () {
        this.moveString = this.toString();
        Builder builder = new Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (! this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(((Pawn)(this.pawnMove.getMovedPiece())).getPromotedPiece().movePiece(this));
        builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
    
    @Override
    public String toString () {
        return this.pawnMove.toString() + "=Q";
    }
    
}

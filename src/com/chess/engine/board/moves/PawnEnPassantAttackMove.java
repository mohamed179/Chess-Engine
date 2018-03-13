package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;

/**
 *
 * @author Mohamed Kamal
 */
public final class PawnEnPassantAttackMove extends PawnAttackMove {
    
    public PawnEnPassantAttackMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCordinate,
                                   final Piece attackedPiece) {
        super(board, movedPiece, destinationCordinate, false, attackedPiece);
    }
    
    @Override
    public boolean isPawnEnPassantAttackMove () {
        return true;
    }
    
    @Override
    public Board execute () {
        this.moveString = this.toString();
        Builder builder = new Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (! piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            if (! piece.equals(this.attackedPiece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(movedPiece.movePiece(this));
        builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || obj instanceof PawnEnPassantAttackMove && super.equals(obj);
    }
    
}

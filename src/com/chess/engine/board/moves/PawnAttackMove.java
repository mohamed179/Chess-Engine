package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;

/**
 *
 * @author Mohamed Kamal
 */
public class PawnAttackMove extends AttackMove {
    
    public PawnAttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCordinate,
                          final boolean isFirstMove,
                          final Piece attackedPiece) {
        super(board, movedPiece, destinationCordinate, isFirstMove, attackedPiece);
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || obj instanceof PawnAttackMove && super.equals(obj);
    }
    
    @Override
    public String toString () {
        return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) +
               "x" +
               BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
    
}

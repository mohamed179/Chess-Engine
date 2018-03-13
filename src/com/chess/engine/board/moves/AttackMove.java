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
public class AttackMove extends Move {
    
    protected final Piece attackedPiece;
    
    public AttackMove (final Board board,
                       final Piece movedPiece,
                       final int destinationCordinate,
                       final boolean isFirstMove,
                       final Piece attackedPiece) {
        super(board, movedPiece, destinationCordinate, isFirstMove);
        this.attackedPiece = attackedPiece;
    }
    
    @Override
    public boolean isAttackMove () {
        return true;
    }
    
    @Override
    public Piece getAttackedPiece () {
        return attackedPiece;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof AttackMove)) {
            return false;
        }
        final AttackMove move = (AttackMove) obj;
        return this.attackedPiece.equals(move.attackedPiece) && super.equals(move);
    }
    
    @Override
    public int hashCode () {
        return this.attackedPiece.hashCode() + super.hashCode();
    }
    
    @Override
    public String toString () {
        return this.movedPiece.getPieceType().toString().substring(0, 1) +
               "x" +
               BoardUtils.getPositionAtCoordinate(destinationCoordinate);
    }
    
}

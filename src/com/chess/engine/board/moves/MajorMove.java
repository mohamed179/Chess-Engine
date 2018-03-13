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
public final class MajorMove extends Move {
    
    public MajorMove (final Board board,
                      final Piece movedPiece,
                      final int destinationCordinate,
                      final boolean isFirstMove) {
        super(board, movedPiece, destinationCordinate, isFirstMove);
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || (obj instanceof MajorMove && super.equals(obj));
    }
    
    @Override
    public int hashCode () {
        return super.hashCode();
    }
    
    @Override
    public String toString () {
        return this.movedPiece.getPieceType().toString() + 
               BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
    
}

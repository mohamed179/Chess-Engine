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
public final class PawnMove extends Move {

    public PawnMove(final Board board,
                    final Piece movedPiece,
                    final int destinationCoordinate,
                    final boolean isFirstMove) {
        super(board, movedPiece, destinationCoordinate, isFirstMove);
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || obj instanceof PawnMove && super.equals(obj);
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
    
}

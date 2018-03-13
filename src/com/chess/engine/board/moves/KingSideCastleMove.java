package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

/**
 *
 * @author Mohamed Kamal
 */
public final class KingSideCastleMove extends CastleMove {
    
    public KingSideCastleMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Rook castleRook,
                              final int castleRookStart,
                              final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || (obj instanceof KingSideCastleMove && super.equals(obj));
    }
    
    @Override
    public String toString () {
        return "O-O";
    }
    
}

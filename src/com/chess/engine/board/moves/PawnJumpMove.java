package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Pawn;

/**
 *
 * @author Mohamed Kamal
 */
public final class PawnJumpMove extends Move {

    public PawnJumpMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate, true);
    }

    @Override
    public Board execute() {
        this.moveString = toString();
        Builder builder = new Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (! piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
    
    @Override
    public boolean equals (Object obj) {
        return this == obj || obj instanceof PawnJumpMove && super.equals(obj);
    }
    
    @Override
    public String toString () {
        return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
    
}

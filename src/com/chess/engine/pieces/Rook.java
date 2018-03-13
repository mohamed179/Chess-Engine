package com.chess.engine.pieces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Tile;
import com.chess.engine.board.moves.*;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Mohamed Kamal
 */
public class Rook extends Piece {
    
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-8, -1, 1, 8};
    
    public Rook (final int piecePosition,
                 final Alliance pieceAlliance) {
        super(Piece.PieceType.ROOK, piecePosition, pieceAlliance, true);
    }
    
    public Rook (final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        super(Piece.PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
    }
    
    public final Collection<Move> calculatedLegalMoves (Board board) {
        
        final List<Move> legalMoves = new ArrayList();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (true) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset) ||
                    isEighthColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset)) {
                    break;
                }
                
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValedCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (! candidateDestinationTile.isOccupated()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate, this.isFirstMove));
                    } else {
                        Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        Alliance destinationPieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != destinationPieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination));
                        }
                        break; // so the rook can not go more over on this direction :D
                    }
                } else {
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
        
    }
    
    private static final boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && candidateOffset == -1;
    }
    
    private static final boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && candidateOffset == 1;
    }
    
    @Override
    public int getValue () {
        return 5;
    }
    
    @Override
    public Rook movePiece (final Move move) {
        return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
    }
    
    @Override
    public String toString () {
        return Piece.PieceType.ROOK.toString();
    }
    
}

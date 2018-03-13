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
import com.chess.engine.player.Player;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import com.google.common.collect.ImmutableList;

/**
 *
 * @author Mohamed Kamal
 */
public class King extends Piece {
    
    private final int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    
    public King (final int piecePosition,
                 final Alliance pieceAlliance) {
        super(Piece.PieceType.KING, piecePosition, pieceAlliance, true);
    }
    
    public King (final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        super(Piece.PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }
    
    @Override
    public final Collection<Move> calculatedLegalMoves (Board board) {
        final List<Move> legalMoves = new ArrayList();
        
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
             final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValedCoordinate(candidateDestinationCoordinate)) {
                
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (! candidateDestinationTile.isOccupated()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate, this.isFirstMove));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance destinationPieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != destinationPieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination));
                    }
                }
            }
        }
        
        // TODO: calculate king castels...
//        legalMoves.addAll(board.getCurrentPlayer().calculatedKingCastles(board.getCurrentPlayer().getLegalMoves(),
//                                                                         board.getCurrentPlayer().getOpponent().getLegalMoves()));
        
        return ImmutableList.copyOf(legalMoves);
    }
    
    private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);
    }
    
    private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);
    }
    
    @Override
    public int getValue () {
        return 2;
    }
    
    @Override
    public King movePiece (final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
    }
    
    @Override
    public String toString () {
        return Piece.PieceType.KING.toString();
    }
    
}

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
import com.chess.engine.board.moves.Move;
import com.chess.engine.board.moves.PawnMove;
import com.chess.engine.board.moves.PawnJumpMove;
import com.chess.engine.board.moves.PawnAttackMove;
import com.chess.engine.board.moves.PawnPromotionMove;
import com.chess.engine.board.moves.PawnEnPassantAttackMove;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import com.google.common.collect.ImmutableList;

/**
 *
 * @author Mohamed Kamal
 */
public class Pawn extends Piece {
    
    private final int[] CANDIDATE_MOVE_COORDINATE = {8, 16,   7, 9};
    
    public Pawn (final int piecePosition,
                 final Alliance pieceAlliance) {
        super(Piece.PieceType.PAWN, piecePosition, pieceAlliance, true);
    }
    
    public Pawn (final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        super(Piece.PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }
    
    @Override
    public final Collection<Move> calculatedLegalMoves (Board board) {
        
        final List<Move> legalMoves = new ArrayList();
        
        for (int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset * 
                                                       this.pieceAlliance.getDirection();
            if (BoardUtils.isValedCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (currentCandidateOffset == 8 && (!candidateDestinationTile.isOccupated())) {
                    if (BoardUtils.isPawnPromotionTile(this.pieceAlliance, candidateDestinationCoordinate)) {
                        legalMoves.add(new PawnPromotionMove(new PawnMove(board, this, candidateDestinationCoordinate, this.isFirstMove)));
                    } else {
                        legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate, this.isFirstMove));
                    }
                } else if (currentCandidateOffset == 16 && isFirstMove() && 
                         ((BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                          (BoardUtils.SEVENTH_RANK[this.piecePosition]  && this.pieceAlliance.isBlack()))) {
                    final int behindCandidateDestinationCoordinate = this.piecePosition +  (currentCandidateOffset * this.pieceAlliance.getDirection());
                    final Tile behinCandidateDestinationTile = board.getTile(behindCandidateDestinationCoordinate);
                    if (!behinCandidateDestinationTile.isOccupated() &&
                        !candidateDestinationTile.isOccupated()) {
                        legalMoves.add(new PawnJumpMove(board, this, behindCandidateDestinationCoordinate));
                    }
                } else if (currentCandidateOffset == 7 &&
                         !(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) &&
                         !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) {
                    if (candidateDestinationTile.isOccupated()) {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        if (this.pieceAlliance != pieceAtDestination.pieceAlliance) {
                            if (BoardUtils.isPawnPromotionTile(this.pieceAlliance, candidateDestinationCoordinate)) {
                                legalMoves.add(new PawnPromotionMove(new PawnAttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination)));
                            } else {
                                legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination));
                            }
                        }
                    } else if (board.getEnPassantPawn() != null) {
                        if (board.getEnPassantPawn().getPiecePosition() == this.piecePosition + this.pieceAlliance.getOppositeDirection()) {
                            final Piece enPassantPawn = board.getEnPassantPawn();
                            if (this.pieceAlliance != enPassantPawn.getPieceAlliance()) {
                                legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, enPassantPawn));
                            }
                        }
                    }
                } else if (currentCandidateOffset == 9 &&
                         !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) &&
                         !(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) {
                    if (candidateDestinationTile.isOccupated()) {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        if (this.getPieceAlliance() != pieceAtDestination.getPieceAlliance()) {
                            if (BoardUtils.isPawnPromotionTile(this.pieceAlliance, candidateDestinationCoordinate)) {
                                legalMoves.add(new PawnPromotionMove(new PawnAttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination)));
                            } else {
                                legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, this.isFirstMove, pieceAtDestination));
                            }
                        }
                    } else if (board.getEnPassantPawn() != null) {
                        if (board.getEnPassantPawn().getPiecePosition() == this.piecePosition + this.pieceAlliance.getDirection()) {
                            final Piece enPassantPawn = board.getEnPassantPawn();
                            if (this.pieceAlliance != enPassantPawn.getPieceAlliance()) {
                                legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, enPassantPawn));
                            }
                        }
                    }
                } 
            }
        }
        
        return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public int getValue () {
        return 1;
    }
    
    public Piece getPromotedPiece () {
        // TODO: Deal with pawn promotion to the seleceted user piece...
        return new Queen(this.piecePosition, this.pieceAlliance, false);
    }
    
    @Override
    public Pawn movePiece (final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
    }
    
    @Override
    public String toString () {
        return Piece.PieceType.PAWN.toString();
    }
    
}

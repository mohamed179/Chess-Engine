package com.chess.engine.player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.moves.MoveStatus;
import com.chess.engine.board.moves.MoveTransition;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.King;
import com.chess.engine.board.moves.Move;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mohamed Kamal
 */
public abstract class Player {
    
    protected final Board board;
    protected final Collection<Move> legalMoves;
    protected final King playerKing;
    protected final boolean isInCheck;
    
    Player (final Board board,
            final Collection<Move> legalMoves,
            final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKIng();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculatedKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculatedAttackMovesOnTile (this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }
    
    private King establishKIng () {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here: not a valid board");
    }
    
    protected static Collection<Move> calculatedAttackMovesOnTile (int piecePosistion,
                                                                Collection<Move> moves) {
        final List<Move> attackMovesOnTile = new ArrayList();
        for(Move move : moves) {
            if (piecePosistion == move.getDestinationCoordinate()) {
                attackMovesOnTile.add(move);
            }
        }
        return ImmutableList.copyOf(attackMovesOnTile);
    }
    
    public Collection<Move> getLegalMoves () {
        return this.legalMoves;
    }
    
    public King getPlayerKing () {
        return this.playerKing;
    }
    
    public final boolean isMoveLegal (Move move) {
        return this.legalMoves.contains(move);
    }
    
    public boolean isInCheck () {
        return this.isInCheck;
    }
    
    public boolean isInCheckMate () {
        return this.isInCheck && !hasEscapeMoves();
    }
    
    public  boolean isStaleMate () {
        return !this.isInCheck && !hasEscapeMoves();
    }
    
    protected boolean hasEscapeMoves () {
        for (final Move move : this.legalMoves) {
            final MoveTransition moveTransition = makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }
    
    //TODO: impelements the methods below...
    public boolean isCasteled () {
        return false;
    }
    
    public MoveTransition makeMove (final Move move) {
        
        if (! isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL);
        }
        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculatedAttackMovesOnTile(
                transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.getCurrentPlayer().getLegalMoves());
        if (! kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
        
    }
    
    public abstract Collection<Piece> getActivePieces ();
    
    public abstract Alliance getAlliance ();
    
    public abstract Player getOpponent ();
    
    public abstract Collection<Move> calculatedKingCastles (final Collection<Move> playerLegals,
                                                            final Collection<Move> opponentLegals);
    
}

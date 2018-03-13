package com.chess.engine.player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;
import com.chess.engine.board.moves.Move;
import com.chess.engine.board.moves.KingSideCastleMove;
import com.chess.engine.board.moves.QueenSideCastleMove;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Mohamed Kamal
 */
public class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final Collection<Move> legalMoves,
                       final Collection<Move> opponentMoves) {
        super(board, legalMoves, opponentMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }
    
    @Override
    public Alliance getAlliance () {
        return Alliance.BLACK;
    }
    
    @Override
    public Player getOpponent () {
        return this.board.getWhitePlayer();
    }
    
    @Override
    public Collection<Move> calculatedKingCastles (final Collection<Move> playerLegals,
                                                      final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList();
        if (this.playerKing.isFirstMove() && ! this.isInCheck()) {
            
            // for king side castling...
            if (! this.board.getTile(5).isOccupated() &&
                ! this.board.getTile(6).isOccupated()) {
                final Tile kingRookTile = this.board.getTile(7);
                if (kingRookTile.getPiece().getPieceType().isRook() &&
                    kingRookTile.isOccupated() &&
                    kingRookTile.getPiece().isFirstMove()) {
                    if (Player.calculatedAttackMovesOnTile(5, opponentLegals).isEmpty() &&
                        Player.calculatedAttackMovesOnTile(6, opponentLegals).isEmpty()) {
                        kingCastles.add(new KingSideCastleMove(this.board,
                                                               this.playerKing,
                                                               this.playerKing.getPiecePosition() + 2,
                                                               (Rook) kingRookTile.getPiece(),
                                                               kingRookTile.getTileCoordinate(),
                                                               kingRookTile.getTileCoordinate() - 2));
                    }
                }
            }
            
            // for queen side castling...
            if (! this.board.getTile(1).isOccupated() &&
                ! this.board.getTile(2).isOccupated() &&
                ! this.board.getTile(3).isOccupated()) {
                final Tile queenRookTile = this.board.getTile(0);
                if (queenRookTile.getPiece().getPieceType().isRook() &&
                    queenRookTile.isOccupated() &&
                    queenRookTile.getPiece().isFirstMove()) {
                    if (Player.calculatedAttackMovesOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculatedAttackMovesOnTile(3, opponentLegals).isEmpty()) {
                        kingCastles.add(new QueenSideCastleMove(this.board,
                                                                this.playerKing,
                                                                this.playerKing.getPiecePosition() - 2,
                                                                (Rook) queenRookTile.getPiece(),
                                                                queenRookTile.getTileCoordinate(),
                                                                queenRookTile.getTileCoordinate() + 3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
    
}

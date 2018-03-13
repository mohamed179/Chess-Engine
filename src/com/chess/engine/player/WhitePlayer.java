package com.chess.engine.player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.Alliance;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import com.chess.engine.board.moves.KingSideCastleMove;
import com.chess.engine.board.moves.QueenSideCastleMove;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mohamed Kamal
 */
public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> legalMoves,
                       final Collection<Move> opponentMoves) {
        super(board, legalMoves, opponentMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }
    
    @Override
    public Alliance getAlliance () {
        return Alliance.WHITE;
    }
    
    @Override
    public Player getOpponent () {
        return this.board.getBlackPlayer();
    }
    
    @Override
    public Collection<Move> calculatedKingCastles (final Collection<Move> playerLegals,
                                                      final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList();
        if (this.playerKing.isFirstMove() && ! this.isInCheck()) {
            
            // for king side castling...
            if (! this.board.getTile(61).isOccupated() &&
                ! this.board.getTile(62).isOccupated()) {
                final Tile kingRookTile = this.board.getTile(63);
                if (kingRookTile.isOccupated() &&
                    kingRookTile.getPiece().getPieceType().isRook() &&
                    kingRookTile.getPiece().isFirstMove()) {
                    if (Player.calculatedAttackMovesOnTile(61, opponentLegals).isEmpty() &&
                        Player.calculatedAttackMovesOnTile(62, opponentLegals).isEmpty()) {
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
            if (! this.board.getTile(57).isOccupated() &&
                ! this.board.getTile(58).isOccupated() &&
                ! this.board.getTile(59).isOccupated()) {
                final Tile queenRookTile = this.board.getTile(56);
                if (queenRookTile.isOccupated() &&
                    queenRookTile.getPiece().getPieceType().isRook() &&
                    queenRookTile.getPiece().isFirstMove()) {
                    if (Player.calculatedAttackMovesOnTile(58, opponentLegals).isEmpty() &&
                        Player.calculatedAttackMovesOnTile(59, opponentLegals).isEmpty()) {
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

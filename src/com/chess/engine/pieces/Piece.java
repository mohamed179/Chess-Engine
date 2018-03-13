package com.chess.engine.pieces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Kamal
 */

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import java.util.Collection;

public abstract class Piece {
    
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected boolean isFirstMove;
    private final int cachedHashCode;
    
    public Piece (final PieceType pieceType,
                  final int piecePosition,
                  final Alliance pieceAlliance,
                  final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = calculateHashCode();
    }
    
    public PieceType getPieceType () {
        return pieceType;
    }

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }
    
    public int getPiecePosition () {
        return piecePosition;
    }
    
    public boolean isFirstMove () {
        return isFirstMove;
    }
    
    private int calculateHashCode () {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceAlliance.hashCode();
        result = 31 * result + this.piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }
    
    @Override
    public int hashCode () {
        return this.cachedHashCode;
    }
    
    @Override
    public boolean equals (final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Piece)) {
            return false;
        }
        final Piece piece = (Piece) obj;
        return this.piecePosition == piece.getPiecePosition() && this.pieceType == piece.getPieceType() &&
               this.pieceAlliance == piece.getPieceAlliance() && this.isFirstMove == piece.isFirstMove();
    }
    
    public abstract Collection<Move> calculatedLegalMoves (Board board);
    
    public abstract Piece movePiece (final Move move);
    
    public abstract int getValue ();
    
    public enum PieceType {
        
        PAWN("P") {
            @Override
            public boolean isKing () {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing () {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing () {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing () {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing () {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing () {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };
        
        private final String pieceName;

        PieceType(String pieceName) {
            this.pieceName = pieceName;
        }
        
        @Override
        public String toString () {
            return pieceName;
        }
        
        public abstract boolean isKing ();
        public abstract boolean isRook ();
        
    }
    
}

package com.chess.engine.board;

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
import com.chess.engine.pieces.Piece;

public class OccupatedTile extends Tile {
    
    private final Piece pieceOnTile;
    
    OccupatedTile (final int tileCordinate, final Piece pieceOnTile) {
        super(tileCordinate);
        this.pieceOnTile = pieceOnTile;
    }
    
    @Override
    public boolean isOccupated () {
        return true;
    }
    
    @Override
    public Piece getPiece () {
        return pieceOnTile;
    }
    
    @Override
    public String toString () {
        return pieceOnTile.getPieceAlliance() == Alliance.BLACK ? pieceOnTile.toString().toLowerCase() :
               pieceOnTile.toString();
    }
    
}

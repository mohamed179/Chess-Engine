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

import com.chess.engine.pieces.Piece;

public class EmptyTile extends Tile {
    
    EmptyTile (final int tileCordinate) {
        super(tileCordinate);
    }
    
    @Override
    public boolean isOccupated () {
        return false;
    }
    
    @Override
    public Piece getPiece () {
        return null;
    }
    
    @Override
    public String toString () {
        return "-";
    }
    
}

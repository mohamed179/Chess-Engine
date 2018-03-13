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
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    
    protected final int tileCoordinate;
    
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
    
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++)
            emptyTileMap.put(i, new EmptyTile(i));
        return ImmutableMap.copyOf(emptyTileMap);
    }
    
    public static Tile createTile (final int tileCordinate, Piece piece) {
        return (piece != null ? new OccupatedTile(tileCordinate, piece) 
                             : EMPTY_TILES_CACHE.get(tileCordinate));
    }
    
    public Tile (final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    
    public int getTileCoordinate () {
        return this.tileCoordinate;
    }
    
    public abstract boolean isOccupated ();
    
    public abstract Piece getPiece ();
    
}

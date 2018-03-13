package com.chess.engine.board;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.Alliance;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

/**
 *
 * @author Mohamed Kamal
 */
public final class BoardUtils {
    
    public static final boolean[] FIRST_COLUMN   = initColumn(0);
    public static final boolean[] SECOND_COLUMN  = initColumn(1);
    public static final boolean[] THIRD_COLUMN   = initColumn(2);
    public static final boolean[] FOURTH_COLUMN  = initColumn(3);
    public static final boolean[] FIFTH_COLUMN   = initColumn(4);
    public static final boolean[] SIXTH_COLUMN   = initColumn(5);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN  = initColumn(7);
    
    public static final boolean[] FIRST_RANK   = initRow(56);
    public static final boolean[] SECOND_RANK  = initRow(48);
    public static final boolean[] THIRD_RANK   = initRow(40);
    public static final boolean[] FOURTH_RANK  = initRow(32);
    public static final boolean[] FIFTH_RANK   = initRow(24);
    public static final boolean[] SIXTH_RANK   = initRow(16);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] EIGHTH_RANK  = initRow(0);
    
    private static final String ALGEBRIC_NOTATION[] = initAlgebricNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();
    
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES_PER_COLUMN = 8;
    
    private BoardUtils () {
        throw new RuntimeException("cannot instantiate this class!");
    }
    
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);
        return column;
    }
    
    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);
        return row;
    }
    
    private static String[] initAlgebricNotation () {
        final String[] positions = new String[NUM_TILES];
        for (int i = 0; i < NUM_TILES; i++) {
            positions[i] = "" + ((char) ('a' + i % NUM_TILES_PER_ROW));
            positions[i] = positions[i] + (8 - i / 8);
        }
        return positions;
    }
    
    private static Map<String, Integer> initPositionToCoordinateMap () {
        final Map<String, Integer> coordinates = new HashMap();
        String algebricNotation;
        for (int i = 0; i < NUM_TILES; i++) {
            algebricNotation = "" + ((char) ('a' + i % NUM_TILES_PER_ROW));
            algebricNotation = algebricNotation + (8 - i / 8);
            coordinates.put(algebricNotation, i);
        }
        return ImmutableMap.copyOf(coordinates);
    }

    public static final boolean isValedCoordinate(final int cordinate) {
        return cordinate >= 0 && cordinate < 64;
    }
    
    public static final boolean isPawnPromotionTile (final Alliance alliance, final int position) {
        if (alliance.isWhite()) {
            return EIGHTH_RANK[position];
        } else {
            return FIRST_RANK[position];
        }
    }
    
    public static final String getPositionAtCoordinate (final int coordinate) {
        return ALGEBRIC_NOTATION[coordinate];
    }
    
    public static final int getCoordinateAtPosition (final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }
}

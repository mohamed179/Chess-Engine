package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Kamal
 */
public final class NullMove extends Move {

    public NullMove() {
        super(null, 56);
    }

    @Override
    public Board execute() {
        throw new RuntimeException("can not execute the null move!");
    }
    
    @Override
    public int getCurrentCoordinate () {
        return -1;
    }
    
}

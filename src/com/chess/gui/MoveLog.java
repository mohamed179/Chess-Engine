package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.moves.Move;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mohamed Kamal
 */
final class MoveLog {
    
    private final List<Move> moves;
    
    MoveLog () {
        this.moves = new ArrayList<Move>();
    }
    
    public List<Move> getMoves () {
        return this.moves;
    }
    
    public int size () {
        return this.moves.size();
    }
    
    public void addMove (final Move move) {
        this.moves.add(move);
    }
    
    public void clear () {
        this.moves.clear();
    }
    
    public Move removeMove (int index) {
        return this.moves.remove(index);
    }
    
    public boolean removeMove (Move move) {
        return this.moves.remove(move);
    }
    
}

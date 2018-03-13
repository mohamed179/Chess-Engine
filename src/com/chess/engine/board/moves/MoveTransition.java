package com.chess.engine.board.moves;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;

/**
 *
 * @author Mohamed Kamal
 */
public class MoveTransition {
    /* 
       this class conatins all the information that must care about 
       when makeing a move and transate from one board to another.
    */
    
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;
    
    public MoveTransition (final Board transitionBoard,
                           final Move move,
                           final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    public MoveStatus getMoveStatus () {
        return this.moveStatus;
    }
    
    public Board getTrnsitionBoard () {
        return this.transitionBoard;
    }
    
    public Move getMove () {
        return this.move;
    }
    
}

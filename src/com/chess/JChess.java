package com.chess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.gui.Table;

/**
 *
 * @author Mohamed Kamal
 */
public class JChess {
    
    public static void main (String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        
        Table table = new Table();
    }
    
}

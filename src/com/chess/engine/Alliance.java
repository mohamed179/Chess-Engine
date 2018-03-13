package com.chess.engine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.BlackPlayer;

/**
 *
 * @author Mohamed Kamal
 */
public enum Alliance {
    WHITE {
        
        @Override
        public final int getDirection() {
            return -1;
        }
        
        @Override
        public final int getOppositeDirection () {
            return 1;
        }
        
        @Override
        public final boolean isWhite() {
            return true;
        }
        
        @Override
        public final boolean isBlack() {
            return false;
        }
        
        @Override
        public final Player choosePlayer (final WhitePlayer whitePlayer,
                                          final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
        
        @Override
        public String toString () {
            return "WHITE";
        }
        
    },
    BLACK {
        
        @Override
        public final int getDirection() {
            return 1;
        }
        
        @Override
        public final int getOppositeDirection () {
            return -1;
        }
        
        @Override
        public final boolean isWhite() {
            return false;
        }
        
        @Override
        public final boolean isBlack() {
            return true;
        }
        
        @Override
        public final Player choosePlayer (final WhitePlayer whitePlayer,
                                          final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
        
        @Override
        public String toString () {
            return "BLACK";
        }
        
    };
    
    public abstract int getDirection ();
    public abstract int getOppositeDirection ();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract Player choosePlayer (final WhitePlayer whitePlayer,
                                         final BlackPlayer blackPlayer);
    
}

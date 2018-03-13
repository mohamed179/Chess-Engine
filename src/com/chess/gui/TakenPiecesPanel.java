package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.moves.Move;
import com.chess.engine.pieces.Piece;
import com.google.common.primitives.Ints;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Mohamed Kamal
 */
final class TakenPiecesPanel extends JPanel {
    
    private static final Dimension TAKEN_PIECES_PANEL = new Dimension(40, 80);
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    
    private final JPanel northPanel;
    private final JPanel southPanel;
    
    TakenPiecesPanel () {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        this.setPreferredSize(TAKEN_PIECES_PANEL);
    }
    
    public void redo (final MoveLog moveLog) {
        this.northPanel.removeAll();
        this.southPanel.removeAll();
        
        final List<Piece> whiteTakenPieces = new ArrayList();
        final List<Piece> blackTakenPieces = new ArrayList();
        for (Move move : moveLog.getMoves()) {
            if (move.isAttackMove()) {
                final Piece attackedPiece = move.getAttackedPiece();
                if (attackedPiece.getPieceAlliance().isWhite()) {
                    whiteTakenPieces.add(attackedPiece);
                } else if (attackedPiece.getPieceAlliance().isBlack()) {
                    blackTakenPieces.add(attackedPiece);
                } else {
                    throw new RuntimeException("should not reach here");
                }
            }
        }
        
        Collections.sort(whiteTakenPieces, new Comparator<Piece>(){
            
            @Override
            public int compare(Piece piece1, Piece piece2) {
                return Ints.compare(piece1.getValue(), piece2.getValue());
            }
            
        });
        Collections.sort(blackTakenPieces, new Comparator<Piece>(){
            
            @Override
            public int compare(Piece piece1, Piece piece2) {
                return Ints.compare(piece1.getValue(), piece2.getValue());
            }
            
        });
        
        for (Piece piece : whiteTakenPieces) {
            try {
                final String path = TilePanel.PIECE_ICONS_PATH + 
                                    piece.getPieceAlliance().toString().substring(0, 1) +
                                    piece.getPieceType().toString() + ".png";
                final BufferedImage tileImage = ImageIO.read(new File(path));
                final Image image = tileImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                this.northPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for (Piece piece : blackTakenPieces) {
            try {
                final String path = TilePanel.PIECE_ICONS_PATH + 
                                    piece.getPieceAlliance().toString().substring(0, 1) +
                                    piece.getPieceType().toString() + ".png";
                final BufferedImage tileImage = ImageIO.read(new File(path));
                final Image image = tileImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                this.southPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.validate();
        this.repaint();
    }
    
}

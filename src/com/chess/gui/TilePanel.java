package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import com.chess.engine.board.moves.MoveTransition;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.JLayeredPane;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed Kamal
 */
final class TilePanel extends JPanel {
    
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Color LIGHT_TILE_COLOR = new Color(240, 217, 181); // Color(159, 144, 176); // Color(206, 215, 224); //Color(238,238,210);
    private static final Color DARK_TILE_COLOR  = new Color(181, 136, 99);  // Color(125, 74, 141);  // Color(78, 130, 178);  //Color(118,150,86);
    private static final Color HIGHLIGHTED_TILE_COLOR  = new Color(173, 177, 135);
    private static final Color CHECKED_KING_TILE_COLOR  = new Color(255, 0, 0);
    static final String PIECE_ICONS_PATH = "images/";
    
    private final int tileId;
    
    TilePanel (final BoardPanel boardPanel,
               final Board board,
               final int tileId) {
        super(new GridBagLayout());
        this.tileId = tileId;
        this.setPreferredSize(TILE_PANEL_DIMENSION);
        this.assignTileColor();
        this.assignTilePieceIcon();
        this.addMouseListener(new TilePanelMouseListener());
        this.validate();
    }
    
    public int getTileId () {
        return this.tileId;
    }
    
    private void assignTileColor () {
        if (BoardUtils.EIGHTH_RANK[tileId] ||
                BoardUtils.SIXTH_RANK[tileId] ||
                BoardUtils.FOURTH_RANK[tileId] ||
                BoardUtils.SECOND_RANK[tileId]) {
            this.setBackground(this.tileId % 2 == 0 ? LIGHT_TILE_COLOR : DARK_TILE_COLOR);
        } else {
            this.setBackground(this.tileId % 2 == 0 ? DARK_TILE_COLOR : LIGHT_TILE_COLOR);
        }
    }
    
    private void assignTilePieceIcon () {
        this.removeAll();
        this.setLayout(new BorderLayout());
        if (Table.chessBoard.getTile(tileId).isOccupated()) {
            try {
                if ((Table.chessBoard.getCurrentPlayer().isInCheck() ||
                     Table.chessBoard.getCurrentPlayer().isInCheckMate()) &&
                     Table.chessBoard.getTile(this.tileId).getPiece().getPieceType().isKing() &&
                     Table.chessBoard.getTile(this.tileId).getPiece().getPieceAlliance() ==
                     Table.chessBoard.getCurrentPlayer().getAlliance()) {
                    try {
                        final String path = PIECE_ICONS_PATH + "redCheck.png";
                        final BufferedImage tileImage = ImageIO.read(new File(path));
                        final Image image = tileImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                        this.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
                    } catch (IOException ex) {
                        System.err.println(ex.toString());
                    }
                }
                final String path = PIECE_ICONS_PATH + 
                                    Table.chessBoard.getTile(tileId).getPiece().getPieceAlliance().toString().substring(0, 1) +
                                    Table.chessBoard.getTile(tileId).getPiece().getPieceType().toString() + ".png";
                final BufferedImage tileImage = ImageIO.read(new File(path));
                final Image image = tileImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                this.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
            } catch (IOException ex) {
                final String path = PIECE_ICONS_PATH + 
                                    Table.chessBoard.getTile(tileId).getPiece().getPieceAlliance().toString().substring(0, 1) +
                                    Table.chessBoard.getTile(tileId).getPiece().getPieceType().toString() + ".png";
                System.out.println(path);
                System.out.println("icon not found!");
            }
        }
    }
    
    void drawTile (final Board board) {
        this.assignTileColor();
        this.assignTilePieceIcon();
        this.validate();
        this.repaint();
    }
    
    public void setSelected (final boolean selectIt) {
        // TODO: set the selected tile...
        if (selectIt) {
            //this.setBorder(border);
        } else {
            
        }
    }
    
    public void setHighlighted (final boolean highlightIt) {
        if (Table.chessBoard.getTile(this.tileId).getPiece() == null) {
            if (highlightIt) {
                try {
                    final String path = PIECE_ICONS_PATH + "greenDot.png";
                    final BufferedImage tileImage = ImageIO.read(new File(path));
                    final Image image = tileImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    this.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
                } catch (IOException ex) {
                    System.err.println(ex.toString());
                }
            } else {
                this.assignTileColor();
                this.assignTilePieceIcon();
            }
        } else {
            if (highlightIt) {
                this.setBackground(HIGHLIGHTED_TILE_COLOR);
            } else {
                this.assignTileColor();
            }
        }
        this.validate();
        this.repaint();
    }
    
    private class TilePanelMouseListener implements MouseListener {
        
        public TilePanelMouseListener () {
            
        }

        @Override
        public void mouseClicked(final MouseEvent event) {
            System.out.println("You have just clicked the tile number : " + tileId);
            if (isLeftMouseButton(event)) {
                if (Table.sourceTile == null) {
                    
                    // first click...
                    Table.sourceTile = Table.chessBoard.getTile(tileId);
                    Table.humanMovedPiece = Table.sourceTile.getPiece();
                    if (Table.humanMovedPiece == null) {
                        
                        // clicked on empty tile...
                        Table.sourceTile = null;
                        System.out.println("you clicked on an empty tile");
                    } else if (Table.humanMovedPiece.getPieceAlliance() == 
                               Table.chessBoard.getCurrentPlayer().getOpponent().getAlliance()) {
                        
                        // clicked on a piece not belong to the current player...
                        Table.sourceTile = null;
                        Table.humanMovedPiece = null;
                        System.out.println("you clicked on a tile not belogn to the current player");
                    }
                    if (Table.sourceTile != null) {
                        Table.boardPanel.informTileSelection(Table.chessBoard, tileId);
                        System.out.println("you have selected a tile");
                    }
                } else if (Table.sourceTile.getTileCoordinate() == tileId) {
                    
                    // second click, and clicked on the same tile...
                    Table.sourceTile      = null;
                    Table.humanMovedPiece = null;
                    Table.boardPanel.informTileUnselection();
                    System.out.println("you clicked on the same tile, so unselect it");
                } else {
                    
                    // second click, and change the selected tile or move...
                    Table.destinationTile = Table.chessBoard.getTile(tileId);
                    if (Table.destinationTile.getPiece() != null) {
                        
                        if (Table.destinationTile.getPiece().getPieceAlliance() == 
                            Table.chessBoard.getCurrentPlayer().getAlliance()) {
                            
                            // change the selected Tile...
                            Table.sourceTile = Table.chessBoard.getTile(tileId);
                            Table.humanMovedPiece = Table.sourceTile.getPiece();
                            Table.destinationTile = null;
                            Table.boardPanel.informTileUnselection();
                            Table.boardPanel.informTileSelection(Table.chessBoard, tileId);
                            System.out.println("you changed the selected tile");
                            return;
                        }
                    }
                    
                    // major move...
                    final Move move = Move.MoveFactory.createMove(Table.chessBoard,
                                                                  Table.sourceTile.getTileCoordinate(),
                                                                  Table.destinationTile.getTileCoordinate());
                    final MoveTransition moveTransition = Table.chessBoard.getCurrentPlayer().makeMove(move);
                    if (moveTransition.getMoveStatus().isDone()) {
                        Table.chessBoard = moveTransition.getTrnsitionBoard();
                        Table.moveLog.addMove(move);
                        SwingUtilities.invokeLater(() -> {
                            Table.boardPanel.drawBoard (Table.chessBoard);
                            if (move.isAttackMove()) {
                                Table.takenPiecesPanel.redo(Table.moveLog);
                            }
                            Table.gameHistoryPanel.redo(Table.chessBoard, Table.moveLog);
                            Table.boardPanel.informMoveDone();
                        });
                        // then reset every thing...
                        Table.sourceTile      = null;
                        Table.humanMovedPiece = null;
                        Table.destinationTile = null;
                        Table.boardPanel.informTileUnselection();
                        System.out.println("you made a move");
                    } else {
                        
                        // you unselected the selected tile, so reset every thing...
                        Table.sourceTile      = null;
                        Table.humanMovedPiece = null;
                        Table.destinationTile = null;
                        Table.boardPanel.informTileUnselection();
                        System.out.println("you did not make a move, so un select the selected tile");
                    }
                }
            } else if (isRightMouseButton(event)) {
                
                // reset every thing (unselect if any selected)...
                Table.sourceTile      = null;
                Table.destinationTile = null;
                Table.humanMovedPiece = null;
                Table.boardPanel.informTileUnselection();
                System.out.println("you unselected the selected tile");
            }
        }

        @Override
        public void mousePressed(final MouseEvent event) {
            
        }

        @Override
        public void mouseReleased(final MouseEvent event) {
            
        }

        @Override
        public void mouseEntered(final MouseEvent event) {
            
        }

        @Override
        public void mouseExited(final MouseEvent event) {
            
        }
        
    }
    
}

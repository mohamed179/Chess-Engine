package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Mohamed Kamal
 */
final class BoardPanel extends JPanel {
    
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    
    private final List<TilePanel> boardTiles;
    private final List<TilePanel> highlightedTiles;
    private TilePanel selectedTilePanel;
    private boolean highlightLegalMoves;
    
    BoardPanel (final Board board) {
        super(new GridLayout(BoardUtils.NUM_TILES_PER_ROW, BoardUtils.NUM_TILES_PER_COLUMN));
        this.boardTiles = new ArrayList();
        this.highlightedTiles = new ArrayList();
        this.selectedTilePanel = null;
        this.highlightLegalMoves = false;
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final TilePanel tilePanel = new TilePanel(this, board, i);
            this.boardTiles.add(tilePanel);
            this.add(tilePanel);
        }
        this.setPreferredSize(BOARD_PANEL_DIMENSION);
        this.validate();
    }
    
    public  void setHighlightLegalMove (final boolean highlightLegalMoves) {
        this.highlightLegalMoves = highlightLegalMoves;
        if (highlightLegalMoves && this.selectedTilePanel != null) {
            for (TilePanel tilePanel : highlightedTiles) {
                tilePanel.setHighlighted(true);
            }
        } else {
            for (TilePanel tilePanel : highlightedTiles) {
                tilePanel.setHighlighted(false);
            }
        }
    }
    
    public TilePanel getSelectedTilePanel () {
        return this.selectedTilePanel;
    }
    
    void drawBoard (final Board board) {
        this.removeAll();
        for (TilePanel tilePanel : Table.boardDirection.traverse(boardTiles)) {
            tilePanel.drawTile(board);
            this.add(tilePanel);
        }
        this.validate();
        this.repaint();
    }
    
    private Collection<Move> pieceLegalMoves (final Board board) {
        if (Table.humanMovedPiece != null && Table.humanMovedPiece.getPieceAlliance() ==
                                             Table.chessBoard.getCurrentPlayer().getAlliance()) {
            return Table.humanMovedPiece.calculatedLegalMoves(board);
        }
        return Collections.emptyList();
    }
    
    public void informTileSelection (final Board board,
                                     final int tileId) {
        this.selectedTilePanel = boardTiles.get(tileId);
        this.selectedTilePanel.setSelected(true);
        if (Table.humanMovedPiece != null && Table.humanMovedPiece.getPieceAlliance() ==
                                             board.getCurrentPlayer().getAlliance()) {
            this.highlightedTiles.clear();
            final List<Move> legalMoves = new ArrayList();
            for (final Move move : Table.chessBoard.getCurrentPlayer().getLegalMoves()) {
                if (move.getMovedPiece().equals(Table.humanMovedPiece)) {
                    legalMoves.add(move);
                }
            }
            for (final Move move : legalMoves) {
                final TilePanel tilePanel = boardTiles.get(move.getDestinationCoordinate());
                if (this.highlightLegalMoves) {
                    tilePanel.setHighlighted(true);
                    if (move.isPawnEnPassantAttackMove()) {
                        boardTiles.get(Table.chessBoard.getEnPassantPawn().getPiecePosition()).setHighlighted(true);
                    }
                }
                this.highlightedTiles.add(tilePanel);
                if (move.isPawnEnPassantAttackMove()) {
                    highlightedTiles.add(boardTiles.get(Table.chessBoard.getEnPassantPawn().getPiecePosition()));
                }
            }
        }
    }
    
    public void informTileUnselection () {
        this.selectedTilePanel.setSelected(false);
        this.selectedTilePanel = null;
        if (highlightLegalMoves) {
            for (final TilePanel tilePanel : highlightedTiles) {
                tilePanel.setHighlighted(false);
            }
        }
        this.highlightedTiles.clear();
    }
    
    public void informMoveDone () {
        if (Table.chessBoard.getCurrentPlayer().isInCheckMate() ||
            Table.chessBoard.getCurrentPlayer().isStaleMate()) {
            // TODO: Deal with game end...
        }
    }
    
}

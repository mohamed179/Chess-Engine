package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.Lists;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Mohamed Kamal
 */
public class Table {
    
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    
    private final JFrame gameFrame;
    static TakenPiecesPanel takenPiecesPanel;
    static GameHistoryPanel gameHistoryPanel;
    static MoveLog moveLog;
    static BoardDirection boardDirection;
    static BoardPanel boardPanel;
    static Board chessBoard;
    static Tile sourceTile       = null;
    static Tile destinationTile  = null;
    static Piece humanMovedPiece = null;
    
    public Table () {
        Table.boardDirection = BoardDirection.NORMAL;
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = populateMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setResizable(false);
        Table.chessBoard = Board.createStandardBoard();
        Table.boardPanel = new BoardPanel(chessBoard);
        Table.takenPiecesPanel = new TakenPiecesPanel();
        Table.gameHistoryPanel = new GameHistoryPanel();
        Table.moveLog = new MoveLog();
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(Table.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }
    
    private static JMenuBar populateMenuBar () {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }
    
    private static JMenu createFileMenu () {
        final JMenu fileMenu = new JMenu("File");
        
        final JMenuItem openPGNMenuItem = new JMenuItem("Load PGN File");
        openPGNMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add action to open PGN file...
                System.out.println("open up this pgn file!");
            }
        });
        fileMenu.add(openPGNMenuItem);
        
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: delete the game frame...
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        
        return fileMenu;
    }
    
    private static JMenu createPreferencesMenu () {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                if (boardDirection == BoardDirection.NORMAL) {
                    boardDirection = BoardDirection.FLIPPED;
                } else {
                    boardDirection = BoardDirection.NORMAL;
                }
                boardPanel.drawBoard(chessBoard);
                if (boardPanel.getSelectedTilePanel() != null) {
                    boardPanel.informTileSelection(chessBoard, boardPanel.getSelectedTilePanel().getTileId());
                }
            }
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        
        final JCheckBoxMenuItem highlightLegalMovesCheckBoxMenuItem = new JCheckBoxMenuItem("Highlight Legal Moves", false);
        highlightLegalMovesCheckBoxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table.boardPanel.setHighlightLegalMove(highlightLegalMovesCheckBoxMenuItem.isSelected());
            }
        });
        preferencesMenu.add(highlightLegalMovesCheckBoxMenuItem);
        return preferencesMenu;
    }
    
    public enum BoardDirection {
        
        NORMAL {
            
            @Override
            public List<TilePanel> traverse (final List<TilePanel> boardTiles) {
                return boardTiles;
            }
            
            @Override
            public BoardDirection opposite () {
                return FLIPPED;
            }
            
        },
        FLIPPED {
            
            @Override
            public List<TilePanel> traverse (final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }
            
            @Override
            public BoardDirection opposite () {
                return NORMAL;
            }
            
        };
        
        public abstract List<TilePanel> traverse (final List<TilePanel> boardTiles);
        public abstract BoardDirection opposite ();
    }
    
}

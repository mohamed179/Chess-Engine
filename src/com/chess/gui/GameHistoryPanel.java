package com.chess.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mohamed Kamal
 */
final class GameHistoryPanel extends JPanel {
    
    private final Dimension GAME_HISTORY_PANEL_DIMENSION = new Dimension(100, 400);
    
    private final DataModel dataModel;
    private final JScrollPane scrollPane;
    private final JTable table;
    
    GameHistoryPanel () {
        this.setLayout(new BorderLayout());
        this.dataModel = new DataModel();
        //this.table = new JTable(new DefaultTableModel());
        this.table = new JTable(dataModel);
        this.table.setRowHeight(15);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setColumnHeaderView(this.table.getTableHeader());
        this.scrollPane.setPreferredSize(GAME_HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public void redo (final Board board,
                      final MoveLog moveLog) {
        this.dataModel.clear();
        int currentRow = 0;
        for (Move move : moveLog.getMoves()) {
            final String moveText = move.getMoveString();
            if (move.getMovedPiece().getPieceAlliance().isWhite()) {
                this.dataModel.setValueAt(moveText, currentRow, 0);
            } else if (move.getMovedPiece().getPieceAlliance().isBlack()) {
                this.dataModel.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        
        if (moveLog.size() > 0) {
            final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
            final String moveText = lastMove.getMoveString() + calculateCheckAndCheckMateHash(board);
            lastMove.setMoveString(moveText);
            if (lastMove.getMovedPiece().getPieceAlliance().isWhite()) {
                this.dataModel.setValueAt(moveText, currentRow, 0);
            } else if (lastMove.getMovedPiece().getPieceAlliance().isBlack()) {
                this.dataModel.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow - 1, 1);
            }
            final JScrollBar vertical = this.scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }
        
    }
    
    private static String calculateCheckAndCheckMateHash(final Board board) {
        if (board.getCurrentPlayer().isInCheckMate()) {
            return "#";
        } else if (board.getCurrentPlayer().isInCheck()) {
            return "+";
        } else {
            return "";
        }
    }
    
    private static final class DataModel extends DefaultTableModel {
        
        private static final String[] NAMES = {"White", "Black"};
        
        private List<Row> values;
        
        DataModel () {
            this.values = new ArrayList();
        }
        
        public void clear() {
            this.values.clear();
            this.setRowCount(0);
        }
        
        @Override
        public int getRowCount () {
            if (values == null) {
                values = new ArrayList();
            }
            return this.values.size();
        }
        
        @Override
        public int getColumnCount () {
            return DataModel.NAMES.length;
        }
        
        @Override
        public Object getValueAt (final int row,
                                  final int column) {
            final Row value = values.get(row);
            switch(column) {
                case 0:
                    return value.getWhiteMove();
                case 1:
                    return value.getBlackMove();
                default:
                    return null;
            }
        }
        
        @Override
        public void setValueAt (final Object value,
                                final int row,
                                final int column) {
            final Row currentRow;
            if (row >= this.values.size()) {
                currentRow = new Row();
                values.add(currentRow);
            } else {
                currentRow = values.get(row);
            }
            if (column == 0) {
                currentRow.setWhiteMove((String) value);
                fireTableRowsInserted(row, row);
            } else if (column == 1) {
                currentRow.setBlackMove((String) value);
                this.fireTableCellUpdated(row, column);
            }
        }
        
        @Override
        public Class<?> getColumnClass (final int column) {
            return Move.class;
        }
        
        @Override
        public String getColumnName (final int column) {
            if (column == 0 || column == 1) {
                return NAMES[column];
            } else {
                return null;
            }
        }
        
    }
    
    private static final class Row {
        
        private String whiteMove;
        private String blackMove;
        
        Row () {
            this.whiteMove = null;
            this.blackMove = null;
        }
        
        public String getWhiteMove () {
            return this.whiteMove;
        }
        
        public String getBlackMove () {
            return this.blackMove;
        }
        
        public void setWhiteMove (final String whiteMove) {
            this.whiteMove = whiteMove;
        }
        
        public void setBlackMove (final String blackMove) {
            this.blackMove = blackMove;
        }
        
    }
    
}

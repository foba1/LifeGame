package com.holub.refactoring;

import java.util.ArrayList;

public class CellBoard {

    private int rowLength;
    private int columnLength;

    private Cell[][] cells;

    public CellBoard(int rowLength, int columnLength) {
        this.rowLength = rowLength;
        this.columnLength = columnLength;

        cells = new Cell[rowLength][columnLength];

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                cells[i][j] = new Cell();
            }
        }

        //linking
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {

                ArrayList<Cell> aroundCells = new ArrayList<Cell>();
                for (int a = i - 1; a <= i + 1; a++) {
                    for (int b = j - 1; b <= j + 1; b++) {
                        if (a == -1 || a == rowLength || b == -1 || b == columnLength) {
                            continue;
                        }
                        if (a == i && b == j) {
                            continue;
                        }
                        aroundCells.add(cells[a][b]);
                    }
                }
                cells[i][j].linkAroundCells(aroundCells);

            }
        }
    }

    public void figureCellsNextState() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].figureNextState();
            }
        }
    }

    public void transitionCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].transition();
            }
        }
    }

    public boolean[][] getCellBoard() {
        boolean[][] cellBoard = new boolean[rowLength][columnLength];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cellBoard[i][j] = cells[i][j].isAlive();
            }
        }

        return cellBoard;
    }

    public void putPattern(int startRow, int startColumn, boolean[][] pattern) {
        if (startRow < 0 || startColumn < 0) {
            return;
        }

        int maxRowLength = startRow + pattern.length;
        if (maxRowLength > rowLength) {
            maxRowLength = rowLength;
        }
        int maxColumnLength = startColumn + pattern[0].length;
        if (maxColumnLength > columnLength) {
            maxColumnLength = columnLength;
        }

        for (int i = startRow; i < maxRowLength; i++) {
            for (int j = startColumn; j < maxColumnLength; j++) {
                cells[i][j].setAmAlive(pattern[i - startRow][j - startColumn]);
            }
        }
    }

    public void flipSpecificCell(int row, int column) {
        cells[row][column].filpAmAlive();
    }


    public void clear() {
        for (int row = 0; row < cells.length; ++row) {
            for (int column = 0; column < cells[0].length; ++column) {
                cells[row][column].clear();
            }
        }
    }
}

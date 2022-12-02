package com.holub.refactoring;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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
                    for (int b = j - 1; b < j + 1; b++) {
                        if (a == -1 || a == rowLength || b == -1 || b == columnLength) {
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

    public Boolean[][] getCellBoard(){
        Boolean[][] cellBoard = new Boolean[rowLength][columnLength];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cellBoard[i][j] = cells[i][j].isAlive();
            }
        }

        return cellBoard;
    }

    public void putPattern(int startRow, int startColumn, Boolean[][] pattern)
    {
        if(startRow < 0 || startColumn < 0)
            return;

        int maxRowLength = startRow + pattern.length;
        if(maxRowLength > rowLength) maxRowLength = rowLength;
        int maxColumnLength = startColumn + pattern[0].length;
        if(maxColumnLength > columnLength) maxColumnLength = columnLength;

        for(int i = startRow;i < maxRowLength;i++){
            for(int j =startColumn ;j < maxColumnLength; j ++){
                cells[i][j].setAmAlive(pattern[i][j]);
            }
        }
    }


    void redraw(Graphics g, Rectangle here) {
        Rectangle subcell = new Rectangle(here.x, here.y,
            here.width / rowLength,
            here.height / columnLength);

        for (int row = 0; row < cells.length; ++row) {
            for (int column = 0; column < cells[0].length; ++column) {
                cells[row][column].redraw(g, subcell);
                subcell.translate(subcell.width, 0);
            }
            subcell.translate(-here.width, subcell.height);
        }
    }


    void userClicked(Point here, Rectangle surface) {
        int pixelsPerCell = surface.width / rowLength;
        int row = here.y / pixelsPerCell;
        int column = here.x / pixelsPerCell;
        int rowOffset = here.y % pixelsPerCell;
        int columnOffset = here.x % pixelsPerCell;

        Point position = new Point(columnOffset, rowOffset);
        Rectangle subcell = new Rectangle(0, 0, pixelsPerCell,
            pixelsPerCell);

        cells[row][column].userClicked(position, subcell); //{=Neighborhood.userClicked.call}
    }


    public void clear() {
        for (int row = 0; row < cells.length; ++row) {
            for (int column = 0; column < cells[0].length; ++column) {
                cells[row][column].clear();
            }
        }
    }
}

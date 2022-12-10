package com.holub.refactoring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CellBoardTest {

    @Test
    void squarePatternTest() {
        CellBoard cellBoard = new CellBoard(4, 4);

        boolean[][] squarePattern = new boolean[][]{{true, true}, {true, true}};
        cellBoard.putPattern(1, 1, squarePattern);

        boolean[][] beforeCellBoard = cellBoard.getCellBoard().clone();

        cellBoard.figureCellsNextState();
        cellBoard.transitionCells();

        boolean[][] afterCellBoard = cellBoard.getCellBoard().clone();

        assertTrue(isSamePattern(beforeCellBoard, afterCellBoard));
    }

    @Test
    void blinkingPatternTest() {
        CellBoard cellBoard = new CellBoard(3, 3);

        boolean[][] startPattern = new boolean[][]{{false, true, false}, {true, true, true},
            {false, true, false}};
        cellBoard.putPattern(0, 0, startPattern);
        cellBoard.figureCellsNextState();
        cellBoard.transitionCells();

        boolean[][] afterCellBoard = cellBoard.getCellBoard().clone();
        boolean[][] endPattern = new boolean[][]{{true, true, true}, {true, false, true},
            {true, true, true}};
        assertTrue(isSamePattern(afterCellBoard, endPattern));
    }

    @Test
    void clearBoardTest() {
        CellBoard cellBoard = new CellBoard(3, 3);

        boolean[][] allTruePattern = new boolean[][]{{true, true, true}, {true, true, true},
            {true, true, true}};
        cellBoard.putPattern(0, 0, allTruePattern);

        cellBoard.clear();

        boolean[][] afterCellBoard = cellBoard.getCellBoard().clone();
        boolean[][] allFalsePattern = new boolean[][]{{false, false, false}, {false, false, false},
            {false, false, false}};

        assertTrue(isSamePattern(afterCellBoard, allFalsePattern));
    }

    @Test
    void zeroBoardTest() {
        CellBoard cellBoard = new CellBoard(0, 0);

        boolean[][] allTruePattern = new boolean[][]{{true, true, true}, {true, true, true},
            {true, true, true}};
        cellBoard.putPattern(0, 0, allTruePattern);

        assertTrue(cellBoard.getCellBoard().length == 0);
    }

    private boolean isSamePattern(boolean[][] array1, boolean[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1.length; j++) {
                if (array1[i][j] == array2[i][j]) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}

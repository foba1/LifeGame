package com.holub.refactoring;

import java.util.ArrayList;

public class Cell {

    private boolean amAlive = false;
    private boolean willBeAlive = false;

    private ArrayList<Cell> aroundCells;

    public void linkAroundCells(ArrayList<Cell> aroundCells) {
        this.aroundCells = aroundCells;
    }

    public boolean isAlive() {
        return amAlive;
    }

    public void figureNextState() {
        int neighbors = 0;

        for (Cell cell : aroundCells) {
            if (cell.isAlive()) {
                neighbors++;
            }
        }

        willBeAlive = (neighbors == 3 || (amAlive && neighbors == 2));
    }

    public boolean transition() {
        boolean changed = isStable();
        amAlive = willBeAlive;
        return changed;
    }

    private boolean isStable() {
        return amAlive == willBeAlive;
    }

    public void filpAmAlive() {
        amAlive = !amAlive;
    }

    public void setAmAlive(boolean amAlive) {
        this.amAlive = amAlive;
    }

    public void clear() {
        amAlive = willBeAlive = false;
    }
}

package com.holub.refactoring;

import com.holub.ui.Colors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Cell {

    private static final Color BORDER_COLOR = Colors.DARK_YELLOW;
    private static final Color LIVE_COLOR = Color.RED;
    private static final Color DEAD_COLOR = Colors.LIGHT_YELLOW;

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
            cell.isAlive();
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

    public void redraw(Graphics g, Rectangle here) {
        g = g.create();
        g.setColor(amAlive ? LIVE_COLOR : DEAD_COLOR);
        g.fillRect(here.x + 1, here.y + 1, here.width - 1, here.height - 1);

        // Doesn't draw a line on the far right and bottom of the
        // grid, but that's life, so to speak. It's not worth the
        // code for the special case.

        g.setColor(BORDER_COLOR);
        g.drawLine(here.x, here.y, here.x, here.y + here.height);
        g.drawLine(here.x, here.y, here.x + here.width, here.y);
        g.dispose();
    }

    public void userClicked(Point here, Rectangle surface) {
        amAlive = !amAlive;
    }

    public void setAmAlive(boolean amAlive) {
        this.amAlive = amAlive;
    }

    public void clear() {
        amAlive = willBeAlive = false;
    }
}

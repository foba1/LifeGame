package com.holub.refactoring;

import com.holub.io.Files;
import com.holub.ui.MenuSite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Universe extends JPanel {

    private static final Universe theInstance = new Universe();
    private static final int DEFAULT_GRID_SIZE = 64;
    private static final int DEFAULT_CELL_SIZE = 8;

    private final CellBoard outermostCell;

    private Universe() {

        outermostCell = new CellBoard(DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);

        final Dimension PREFERRED_SIZE = new Dimension(
            DEFAULT_GRID_SIZE * DEFAULT_CELL_SIZE,
            DEFAULT_GRID_SIZE * DEFAULT_CELL_SIZE);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Rectangle bounds = getBounds();
                bounds.height /= DEFAULT_GRID_SIZE;
                bounds.height *= DEFAULT_GRID_SIZE;
                bounds.width = bounds.height;
                setBounds(bounds);
            }
        });

        setBackground(Color.white);
        setPreferredSize(PREFERRED_SIZE);
        setMaximumSize(PREFERRED_SIZE);
        setMinimumSize(PREFERRED_SIZE);
        setOpaque(true);

        addMouseListener // {=Universe.mouse}
            (new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    Rectangle bounds = getBounds();
                    bounds.x = 0;
                    bounds.y = 0;
                    outermostCell.userClicked(e.getPoint(), bounds);
                    repaint();
                }
            });

        MenuSite.addLine(this, "Grid", "Clear",
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    outermostCell.clear();
                    repaint();
                }
            });

        MenuSite.addLine // {=Universe.load.setup}
            (this, "Grid", "Load",
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        doLoad();
                    }
                });

        MenuSite.addLine(this, "Grid", "Store",
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doStore();
                }
            });

        MenuSite.addLine(this, "Grid", "Exit",
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

        //Clock
        ActionListener modifier =                                    //{=startSetup}
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = ((JMenuItem) e.getSource()).getName();
                    char toDo = name.charAt(0);

                    if (toDo == 'T') {
//                        tick();                      // single tick
                    } else {
//                        startTicking(toDo == 'A' ? 500 :      // agonizing
//                            toDo == 'S' ? 150 :      // slow
//                                toDo == 'M' ? 70 :      // medium
//                                    toDo == 'F' ? 30 : 0); // fast
                    }
                }
            };
        // {=midSetup}
        MenuSite.addLine(this, "Go", "Halt", modifier);
        MenuSite.addLine(this, "Go", "Tick (Single Step)", modifier);
        MenuSite.addLine(this, "Go", "Agonizing", modifier);
        MenuSite.addLine(this, "Go", "Slow", modifier);
        MenuSite.addLine(this, "Go", "Medium", modifier);
        MenuSite.addLine(this, "Go", "Fast", modifier); // {=endSetup}
    }

    public static Universe instance() {
        return theInstance;
    }

    private void doLoad() {
        try {
            FileInputStream in = new FileInputStream(
                Files.userSelected(".", ".life", "Life File", "Load"));

//            Clock.instance().stop(); // stop the game and
//            outermostCell.clear(); // clear the board.
//
//            Storable memento = outermostCell.createMemento();
//            memento.load(in);
//            outermostCell.transfer(memento, new Point(0, 0), Cell.LOAD);

            in.close();
        } catch (IOException theException) {
            JOptionPane.showMessageDialog(null, "Read Failed!",
                "The Game of Life", JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    }

    private void doStore() {
        try {
            FileOutputStream out = new FileOutputStream(
                Files.userSelected(".", ".life", "Life File", "Write"));

//            Clock.instance().stop(); // stop the game
//
//            Storable memento = outermostCell.createMemento();
//            outermostCell.transfer(memento, new Point(0, 0), Cell.STORE);
//            memento.flush(out);

            out.close();
        } catch (IOException theException) {
            JOptionPane.showMessageDialog(null, "Write Failed!",
                "The Game of Life", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Override paint to ask the outermost Neighborhood (and any subcells) to draw themselves
     * recursively. All knowledge of screen size is also encapsulated. (The size is passed into the
     * outermost <code>Cell</code>.)
     */
    public void paint(Graphics g) {
        Rectangle panelBounds = getBounds();
        Rectangle clipBounds = g.getClipBounds();

        // The panel bounds is relative to the upper-left
        // corner of the screen. Pretend that it's at (0,0)
        panelBounds.x = 0;
        panelBounds.y = 0;

        outermostCell.redraw(g, panelBounds); // {=Universe.redraw1}
    }

    /**
     * Force a screen refresh by queing a request on the Swing event queue. This is an example of
     * the Active Object pattern (not covered by the Gang of Four). This method is called on every
     * clock tick. Note that the redraw() method on a given <code>Cell</code> does nothing if the
     * <code>Cell</code> doesn't have to be refreshed.
     */
    private void refreshNow() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Graphics g = getGraphics();
                if (g == null) // Universe not displayable
                {
                    return;
                }
                try {
                    Rectangle panelBounds = getBounds();
                    panelBounds.x = 0;
                    panelBounds.y = 0;
                    outermostCell.redraw(g, panelBounds); // {=Universe.redraw2}
                } finally {
                    g.dispose();
                }
            }
        });
    }
}

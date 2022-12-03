package com.holub.refactoring;

import com.holub.ui.MenuSite;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*******************************************************************
 * An implemenation of Conway's Game of Life.
 *
 * @include /etc/license.txt
 */

public final class Life extends JFrame {

    private static JComponent universe;

    private Life() {
        super("The Game of Life. "
            + "(c)2003 Allen I. Holub <http://www.holub.com>");

        // Must establish the MenuSite very early in case
        // a subcomponent puts menus on it.
        MenuSite.establish(this); // {=life.java.establish}

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(Universe.instance(), BorderLayout.CENTER); // {=life.java.install}

        pack();
        setVisible(true);
    }

    public static void main(String[] arguments) {
        new Life();
    }
}

package com.holub.refactoring;

import com.holub.io.Files;
import com.holub.ui.Colors;
import com.holub.ui.MenuSite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Universe extends JPanel {

    private static final Color BORDER_COLOR = Colors.DARK_YELLOW;
    private static final Color LIVE_COLOR = Color.RED;
    private static final Color DEAD_COLOR = Colors.LIGHT_YELLOW;

    private static final Universe theInstance = new Universe();
    private static final int DEFAULT_GRID_SIZE = 64;
    private static final int DEFAULT_CELL_SIZE = 8;
    private int rowLength;
    private int columnLength;

    private final CellBoard outermostCell;

    private Universe() {
        rowLength = DEFAULT_GRID_SIZE;
        columnLength = DEFAULT_GRID_SIZE;

        outermostCell = new CellBoard(rowLength, columnLength);

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
                    int[] point = getCellIndexFromUserClicked(e.getPoint(), bounds);
                	if (PatternManager.instance().IsPatternSelected())
                	{
                		PatternManager.instance().Draw(point[0], point[1]);
                	}
                	else
                	{
                        outermostCell.flipSpecificCell(point[0], point[1]);
                	}
                	repaint();
                }
            });
        
        addMouseListener // {=Universe.mouse}
	        (new MouseAdapter() {
	        	public void mouseExited(MouseEvent e) {
	        		if (PatternManager.instance().IsPatternSelected())
					{
	        			PatternManager.instance().Reset();
						repaint();
					}
	            }
	        }
        );
        
        addMouseMotionListener // {=Universe.mouse}
		    (new MouseAdapter() {
		    	public void mouseMoved(MouseEvent e) {
					if (PatternManager.instance().IsPatternSelected())
					{
						Rectangle bounds = getBounds();
	                    bounds.x = 0;
	                    bounds.y = 0;
	                    int[] point = getCellIndexFromUserClicked(e.getPoint(), bounds);
	                    PatternManager.instance().Show(point[0], point[1]);
						repaint();
					}
				}
			}
		);
        
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

        MenuSite.addLine // {=Universe.load.setup}
            (this, "Grid", "Load Image",
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        doLoadImage();
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
                        Clock.instance().tick();
                    } else {
                        Clock.instance().startTicking(toDo == 'A' ? 500 :      // agonizing
                            toDo == 'S' ? 150 :      // slow
                                toDo == 'M' ? 70 :      // medium
                                    toDo == 'F' ? 30 : 0); // fast
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

        Clock.instance().addClockListener // {=Universe.clock.subscribe}
            (new Clock.Listener() {
                 public void tick() {
                     outermostCell.figureCellsNextState();
                     outermostCell.transitionCells();
                     repaint();
                 }
             }
            );
    }

    private int[] getCellIndexFromUserClicked(Point here, Rectangle surface) {
        int pixelsPerCell = surface.width / rowLength;
        int row = here.y / pixelsPerCell;
        int column = here.x / pixelsPerCell;

        int[] point = {row,column};
        return point;
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
    
    private void doLoadImage() {
        try {
//            FileInputStream in = new FileInputStream(
//                Files.userSelected(".", ".jpg", "JPG File", "Load Image"));
            
            Image in = ImageIO.read(Files.userSelected(".", ".png", "PNG File", "Load Image"));
            
            int height = in.getHeight(null);
            int width = in.getWidth(null);
            		
            if (height == -1 || width == -1) {
            	throw new Exception("NotImageFile");
            }
            
            BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_BYTE_GRAY);	
            image.getGraphics().drawImage(in.getScaledInstance(64, 64, Image.SCALE_DEFAULT), 0, 0 , null);
            byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            
            Boolean[][] pattern = new Boolean[64][64];
            
            for(int i = 0; i < 64; i++)
            {
                for(int j = 0; j < 64; j++)
                {
                	
                	if (pixels[i*64 + j] < 0)
                	{
                		pattern[i][j] = false;
                	}
                	else
                	{
                		pattern[i][j] = true;
                	}
                }
            }
            
            Clock.instance().stop(); // stop the game and
            outermostCell.clear(); // clear the board.
            

//            Storable memento = outermostCell.createMemento();
//            memento.load(in);
//            outermostCell.transfer(memento, new Point(0, 0), Cell.LOAD);

            Universe.instance().putPattern(0, 0, pattern);
            
//            in.close();
        }
        catch (IOException theException) {
            JOptionPane.showMessageDialog(null, "Read Failed!",
                "The Game of Life", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Read Failed!\nSelected file is not an image files\nPlease select an image file.",
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

        redraw(g, panelBounds); // {=Universe.redraw1}
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
                    redraw(g, panelBounds); // {=Universe.redraw2}
                } finally {
                    g.dispose();
                }
            }
        });
    }

    void redraw(Graphics g, Rectangle here) {
        Rectangle subcell = new Rectangle(here.x, here.y,
            here.width / rowLength,
            here.height / columnLength);

        Boolean[][] cells = outermostCell.getCellBoard();

        for (int row = 0; row < rowLength; ++row) {
            for (int column = 0; column < columnLength; ++column) {

                g = g.create();

                g.setColor(cells[row][column] ? LIVE_COLOR : DEAD_COLOR);
                g.fillRect(subcell.x + 1, subcell.y + 1, subcell.width - 1, subcell.height - 1);

                // Doesn't draw a line on the far right and bottom of the
                // grid, but that's life, so to speak. It's not worth the
                // code for the special case.

                g.setColor(BORDER_COLOR);
                g.drawLine(subcell.x, subcell.y, subcell.x, subcell.y + subcell.height);
                g.drawLine(subcell.x, subcell.y, subcell.x + subcell.width, subcell.y);

                subcell.translate(subcell.width, 0);
            }
            subcell.translate(-here.width, subcell.height);
        }
    }
    
    public Boolean[][] getCellBoard(){
        return outermostCell.getCellBoard();
    }
    
    public void putPattern(int startRow, int startColumn, Boolean[][] pattern)
    {
    	outermostCell.putPattern(startRow, startColumn, pattern);
    }
}

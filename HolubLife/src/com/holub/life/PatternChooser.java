package com.holub.life;

import java.io.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.holub.io.Files;
import com.holub.ui.MenuSite;

import com.holub.life.Cell;
import com.holub.life.Storable;
import com.holub.life.Clock;
import com.holub.life.Neighborhood;
import com.holub.life.Resident;

/**
 * The Universe is a mediator that sits between the Swing
 * event model and the Life classes. It is also a singleton,
 * accessed via Universe.instance(). It handles all
 * Swing events and translates them into requests to the
 * outermost Neighborhood. It also creates the Composite
 * Neighborhood.
 *
 * @include /etc/license.txt
 */

public class PatternChooser extends JPanel
{	//private 		final Cell  	outermostCell;
	private static	final JFileChooser		chooser = Files.fileChooser(".",".life","Life File","Pattern");
	private static	final PatternPreview 	patternPreview = PatternPreview.instance();
	private static 	final GridLayout		layout		= new GridLayout(2,1);
	private static	final PatternChooser 	theInstance = new PatternChooser();

	/** The default height and width of a Neighborhood in cells.
	 *  If it's too big, you'll run too slowly because
	 *  you have to update the entire block as a unit, so there's more
	 *  to do. If it's too small, you have too many blocks to check.
	 *  I've found that 8 is a good compromise.
	 */
//	private static final int  DEFAULT_WIDTH_SIZE = 400;

	/** The size of the smallest "atomic" cell---a Resident object.
	 *  This size is extrinsic to a Resident (It's passed into the
	 *  Resident's "draw yourself" method.
	 */
//	private static final int  DEFAULT_CELL_SIZE = 400;

	// The constructor is private so that the universe can be created
	// only by an outer-class method [Neighborhood.createUniverse()].

	private PatternChooser()
	{	// Create the nested Cells that comprise the "universe." A bug
		// in the current implementation causes the program to fail
		// miserably if the overall size of the grid is too big to fit
		// on the screen.

//		final Dimension PREFERRED_SIZE =
//						new Dimension
//						(  DEFAULT_WIDTH_SIZE,
//							DEFAULT_CELL_SIZE
//						);

		addComponentListener
		(	new ComponentAdapter()
			{	public void componentResized(ComponentEvent e)
				{
					// Make sure that the cells fit evenly into the
					// total grid size so that each cell will be the
					// same size. For example, in a 64x64 grid, the
					// total size must be an even multiple of 63.

					Rectangle bounds = getBounds();
					
					setBounds( bounds );
				}
			}
		);

		setBackground	( Color.white	 );
//		setPreferredSize( PREFERRED_SIZE );
//		setMaximumSize	( PREFERRED_SIZE );
//		setMinimumSize	( PREFERRED_SIZE );
		setOpaque		( true			 );

//		chooser.setPreferredSize(new Dimension(300, 300));
		
//		chooser.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//			    if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
//			    {
//			        System.out.println("approve selection");
//			        selectedFile = chooser.getSelectedFile();
//			    }
//			    else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION))
//			    {
//			        System.out.println("cancel selection");
//			        selectedFile = null;
//			    }
//			}
//			
//		});
		
	}

	/** Singleton Accessor. The Universe object itself is manufactured
	 *  in Neighborhood.createUniverse()
	 */

	public static JFileChooser getChooser()
	{
		return chooser;
	}
	
	public static PatternChooser instance()
	{	
		if (!(theInstance.getLayout() instanceof GridLayout)) {
			theInstance.add(chooser);
			theInstance.add(patternPreview);
			theInstance.setLayout(layout);
		}
		return theInstance;
	}
	
//	private void doLoad()
//	{	try
//		{
//			FileInputStream in = new FileInputStream(
//			   Files.userSelected(".",".life","Life File","Load"));
//
//			Clock.instance().stop();		// stop the game and
//			outermostCell.clear();			// clear the board.
//
//			Storable memento = outermostCell.createMemento();
//			memento.load( in );
//			outermostCell.transfer( memento, new Point(0,0), Cell.LOAD );
//
//			in.close();
//		}
//		catch( IOException theException )
//		{	JOptionPane.showMessageDialog( null, "Read Failed!",
//					"The Game of Life", JOptionPane.ERROR_MESSAGE);
//		}
//		repaint();
//	}
//
//	private void doStore()
//	{	try
//		{
//			FileOutputStream out = new FileOutputStream(
//				  Files.userSelected(".",".life","Life File","Write"));
//
//			Clock.instance().stop();		// stop the game
//
//			Storable memento = outermostCell.createMemento();
//			outermostCell.transfer( memento, new Point(0,0), Cell.STORE );
//			memento.flush(out);
//
//			out.close();
//		}
//		catch( IOException theException )
//		{	JOptionPane.showMessageDialog( null, "Write Failed!",
//					"The Game of Life", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	/** Override paint to ask the outermost Neighborhood
//	 *  (and any subcells) to draw themselves recursively.
//	 *  All knowledge of screen size is also encapsulated.
//	 *  (The size is passed into the outermost <code>Cell</code>.)
//	 */
//
//	public void paint(Graphics g)
//	{
//		Rectangle panelBounds = getBounds();
//		Rectangle clipBounds  = g.getClipBounds();
//
//		// The panel bounds is relative to the upper-left
//		// corner of the screen. Pretend that it's at (0,0)
//		panelBounds.x = 0;
//		panelBounds.y = 0;
//		outermostCell.redraw(g, panelBounds, true);		//{=Universe.redraw1}
//	}
//
//	/** Force a screen refresh by queing a request on
//	 *  the Swing event queue. This is an example of the
//	 *  Active Object pattern (not covered by the Gang of Four).
//	 *  This method is called on every clock tick. Note that
//	 *  the redraw() method on a given <code>Cell</code>
//	 *  does nothing if the <code>Cell</code> doesn't
//	 *  have to be refreshed.
//	 */
//
//	private void refreshNow()
//	{	SwingUtilities.invokeLater
//		(	new Runnable()
//			{	public void run()
//				{	Graphics g = getGraphics();
//					if( g == null )		// Universe not displayable
//						return;
//					try
//					{
//						Rectangle panelBounds = getBounds();
//						panelBounds.x = 0;
//						panelBounds.y = 0;
//						outermostCell.redraw(g, panelBounds, false); //{=Universe.redraw2}
//					}
//					finally
//					{	g.dispose();
//					}
//				}
//			}
//		);
//	}
}

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
{	private static	final JFileChooser		chooser = Files.fileChooser(".",".life","Life File","Pattern");
	private static	final PatternPreview 	patternPreview = PatternPreview.instance();
	private static 	final GridLayout		layout		= new GridLayout(2,1);
	private static	final PatternChooser 	theInstance = new PatternChooser();

	private PatternChooser()
	{	

		addComponentListener
		(	new ComponentAdapter()
			{	public void componentResized(ComponentEvent e)
				{
					Rectangle bounds = getBounds();
					
					setBounds( bounds );
				}
			}
		);

		setBackground	( Color.white	 );
		setPreferredSize( new Dimension(400,800) );
//		setMaximumSize	( new Dimension(400,400) );
		setMinimumSize	( new Dimension(400,800) );
		setOpaque		( true			 );
		
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
	
}

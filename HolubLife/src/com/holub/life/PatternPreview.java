package com.holub.life;

import java.io.*;

import java.awt.*;
import java.awt.event.*;

import com.holub.ui.MenuSite;

public class PatternPreview {
	private static final PatternPreview theInstance = new PatternPreview();
	
	private int curPreviewPattern = -1;
	
	private PatternPreview()
	{
		MenuSite.addLine( this, "Pattern", "Reset",
				new ActionListener()
				{	public void actionPerformed(ActionEvent e)
					{
						SelectPattern(-1);
					}
				}
			);
		MenuSite.addLine( this, "Pattern", "Pattern 1",
				new ActionListener()
				{	public void actionPerformed(ActionEvent e)
					{
						SelectPattern(0);
					}
				}
			);
		MenuSite.addLine( this, "Pattern", "Pattern 2",
				new ActionListener()
				{	public void actionPerformed(ActionEvent e)
					{
						SelectPattern(1);
					}
				}
			);
		MenuSite.addLine( this, "Pattern", "Pattern 3",
				new ActionListener()
				{	public void actionPerformed(ActionEvent e)
					{
						SelectPattern(2);
					}
				}
			);
	}
	
	public static PatternPreview instance()
	{	return theInstance;
	}
	
	public void Show(Point here, Rectangle surface)
	{
		System.out.println("Show pattern " + curPreviewPattern);
	}
	
	public void Draw(Point here, Rectangle surface)
	{
		System.out.println("Draw pattern " + curPreviewPattern);
	}
	
	public boolean IsPatternSelected()
	{	return curPreviewPattern != -1;
	}
	
	private void SelectPattern(int pattern) {
		if (curPreviewPattern == pattern)
		{
			curPreviewPattern = -1;
		}
		else
		{
			curPreviewPattern = pattern;
		}
	}
}

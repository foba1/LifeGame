package com.holub.refactoring;

import java.io.*;

import java.awt.*;
import java.awt.event.*;

import com.holub.ui.MenuSite;

public class PatternPreview {
	private static final PatternPreview theInstance = new PatternPreview();
	
	private int curPattern = -1;
	
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
	
	public void Show(int row, int column)
	{
		System.out.println("Show pattern " + curPattern);
	}
	
	public void Draw(int row, int column)
	{
		System.out.println("Draw pattern " + curPattern);
		if (curPattern == 0)
		{
			Boolean[][] pattern = {{true, true}, {true, true}};
			Universe.instance().putPattern(row, column, pattern);
		}
		else if (curPattern == 1)
		{
			Boolean[][] pattern = {{false, true, false}, {false, false, true}, {true, true, true}};
			Universe.instance().putPattern(row, column, pattern);
		}
	}
	
	public boolean IsPatternSelected()
	{	return curPattern != -1;
	}
	
	private void SelectPattern(int pattern) {
		if (curPattern == pattern)
		{
			curPattern = -1;
		}
		else
		{
			curPattern = pattern;
		}
	}
}
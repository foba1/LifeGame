package com.holub.refactoring;

import java.io.*;

import java.awt.*;
import java.awt.event.*;

import com.holub.ui.MenuSite;

public class PatternPreview {
	private static final PatternPreview theInstance = new PatternPreview();
	
	private int curPattern = -1;
	private Boolean[][] curCellBoard;
	private Boolean[][][] pattern = {
			{{true, true}, {true, true}},
			{{false, true, false}, {false, false, true}, {true, true, true}}
	};
	
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
	}
	
	public static PatternPreview instance()
	{	return theInstance;
	}
	
	public void Show(int row, int column)
	{
		if (curPattern < 0 || curPattern > pattern.length) return;
		
		Universe.instance().putPattern(0, 0, curCellBoard);
		Universe.instance().putPattern(row, column, pattern[curPattern]);
	}
	
	public void Draw(int row, int column)
	{
		if (curPattern < 0 || curPattern > pattern.length) return;
		
		Universe.instance().putPattern(row, column, pattern[curPattern]);
		curCellBoard = Universe.instance().getCellBoard();
	}
	
	public boolean IsPatternSelected()
	{	return curPattern != -1;
	}
	
	private void SelectPattern(int pattern) {
		if (curPattern == pattern)
		{
			curPattern = -1;
			curCellBoard = null;
		}
		else
		{
			curPattern = pattern;
			curCellBoard = Universe.instance().getCellBoard();
		}
	}
}
package com.holub.refactoring;

import java.io.*;

import java.awt.*;
import java.awt.event.*;

import com.holub.ui.MenuSite;

public class PatternManager {
	private static final PatternManager theInstance = new PatternManager();
	
	private int curPattern = -1;
	private Boolean[][] curCellBoard;
	private Pattern[] pattern = {new Block(), new Beehive(), new Boat(), new Blinker(), new Beacon(), new Glider(), new Spaceship()};
	
	private PatternManager()
	{
		MenuSite.addLine( this, "Pattern", "Reset",
				new ActionListener()
				{	public void actionPerformed(ActionEvent e)
					{
						selectPattern(-1);
					}
				}
			);
		
		for (int i = 0; i < pattern.length; i++)
		{
			final int _i = i;
			MenuSite.addLine( this, "Pattern", pattern[i].GetName(),
					new ActionListener()
					{	public void actionPerformed(ActionEvent e)
						{
							selectPattern(_i);
						}
					}
				);
		}
	}
	
	public static PatternManager instance()
	{	return theInstance;
	}
	
	public void reset()
	{
		Universe.instance().putPattern(0, 0, curCellBoard);
	}
	
	public void show(int row, int column)
	{
		if (curPattern < 0 || curPattern > pattern.length) return;
		
		Universe.instance().putPattern(0, 0, curCellBoard);
		Universe.instance().putPattern(row, column, pattern[curPattern].GetPattern());
	}
	
	public void draw(int row, int column)
	{
		if (curPattern < 0 || curPattern > pattern.length) return;
		
		Universe.instance().putPattern(row, column, pattern[curPattern].GetPattern());
		curCellBoard = Universe.instance().getCellBoard();
	}
	
	public boolean isPatternSelected()
	{	return curPattern != -1;
	}
	
	private void selectPattern(int pattern) {
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
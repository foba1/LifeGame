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
			{{false, true, true, false}, {true, false, false, true}, {false, true, true, false}},
			{{true, true, false}, {true, false, true}, {false, true, false}},
			{{false, true, false}, {true, false, true}, {false, true, false}},
			{{true, true, true}},
			{{false, true, true, true}, {true, true, true, false}},
			{{true, true, false, false}, {true, true, false, false}, {false, false, true, true}, {false, false, true, true}},
			{{false, true, false}, {false, false, true}, {true, true, true}},
			{{false, true, true, true, true}, {true, false, false, false, true}, {false, false, false, false, true}, {true, false, false, true, false}},
			{{false, true, true, true, true, true, true}, {true, false, false, false, false, false, true}, {false, false, false, false, false, false, true}, {true, false, false, false, false, true, false}, {false, false, true, true, false, false, false}}
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
		
		for (int i = 0; i < pattern.length; i++)
		{
			final int _i = i;
			MenuSite.addLine( this, "Pattern", "Pattern " + (_i + 1),
					new ActionListener()
					{	public void actionPerformed(ActionEvent e)
						{
							SelectPattern(_i);
						}
					}
				);
		}
	}
	
	public static PatternPreview instance()
	{	return theInstance;
	}
	
	public void Reset()
	{
		Universe.instance().putPattern(0, 0, curCellBoard);
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
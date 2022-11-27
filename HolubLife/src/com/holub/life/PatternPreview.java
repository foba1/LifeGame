package com.holub.life;

import java.io.*;

import java.awt.event.*;

import com.holub.ui.MenuSite;

public class PatternPreview {
	private static final PatternPreview theInstance = new PatternPreview();
	
	private int selectedPattern = -1;
	
	private PatternPreview()
	{
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
	
	public void Show()
	{
		System.out.println(selectedPattern);
	}
	
	private void SelectPattern(int pattern) {
		selectedPattern = pattern;
	}
}

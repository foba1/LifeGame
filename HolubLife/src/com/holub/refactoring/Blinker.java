package com.holub.refactoring;

public class Blinker extends Pattern {
	public Blinker() {
		SetName("Blinker Pattern");
		SetPattern(new Boolean[][]{{true, true, true}});
	}
}

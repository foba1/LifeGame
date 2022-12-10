package com.holub.refactoring;

public class Blinker extends Pattern {
	public Blinker() {
		setName("Blinker Pattern");
		setPattern(new Boolean[][]{{true, true, true}});
	}
}

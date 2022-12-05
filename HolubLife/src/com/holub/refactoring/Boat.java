package com.holub.refactoring;

public class Boat extends Pattern {
	public Boat() {
		SetName("Boat Pattern");
		SetPattern(new Boolean[][]{{true, true, false}, {true, false, true}, {false, true, false}});
	}
}

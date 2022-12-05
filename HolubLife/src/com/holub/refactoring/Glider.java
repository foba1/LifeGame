package com.holub.refactoring;

public class Glider extends Pattern {
	public Glider() {
		SetName("Glider Pattern");
		SetPattern(new Boolean[][]{{false, true, false}, {false, false, true}, {true, true, true}});
	}
}

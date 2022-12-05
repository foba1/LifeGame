package com.holub.refactoring;

public class Beacon extends Pattern {
	public Beacon() {
		SetName("Beacon Pattern");
		SetPattern(new Boolean[][]{{true, true, false, false}, {true, true, false, false}, {false, false, true, true}, {false, false, true, true}});
	}
}

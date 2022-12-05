package com.holub.refactoring;

public class Beehive extends Pattern {
	public Beehive() {
		SetName("Beehive Pattern");
		SetPattern(new Boolean[][]{{false, true, true, false}, {true, false, false, true}, {false, true, true, false}});
	}
}

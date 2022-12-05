package com.holub.refactoring;

public class Spaceship extends Pattern {
	public Spaceship() {
		SetName("Spaceship Pattern");
		SetPattern(new Boolean[][]{{false, true, true, true, true}, {true, false, false, false, true}, {false, false, false, false, true}, {true, false, false, true, false}});
	}
}

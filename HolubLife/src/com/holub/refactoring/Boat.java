package com.holub.refactoring;

public class Boat extends Pattern {

    public Boat() {
        setName("Boat Pattern");
        setPattern(new boolean[][]{{true, true, false}, {true, false, true}, {false, true, false}});
    }
}

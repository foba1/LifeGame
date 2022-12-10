package com.holub.refactoring;

public class Glider extends Pattern {

    public Glider() {
        setName("Glider Pattern");
        setPattern(new boolean[][]{{false, true, false}, {false, false, true}, {true, true, true}});
    }
}

package com.holub.refactoring;

public class Beacon extends Pattern {

    public Beacon() {
        setName("Beacon Pattern");
        setPattern(new boolean[][]{{true, true, false, false}, {true, true, false, false},
            {false, false, true, true}, {false, false, true, true}});
    }
}

package com.holub.refactoring;

public class Beehive extends Pattern {

    public Beehive() {
        setName("Beehive Pattern");
        setPattern(new boolean[][]{{false, true, true, false}, {true, false, false, true},
            {false, true, true, false}});
    }
}

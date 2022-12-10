package com.holub.refactoring;

public class Spaceship extends Pattern {

    public Spaceship() {
        setName("Spaceship Pattern");
        setPattern(
            new boolean[][]{{false, true, true, true, true}, {true, false, false, false, true},
                {false, false, false, false, true}, {true, false, false, true, false}});
    }
}

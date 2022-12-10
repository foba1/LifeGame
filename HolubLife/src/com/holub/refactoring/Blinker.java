package com.holub.refactoring;

public class Blinker extends Pattern {

    public Blinker() {
        setName("Blinker Pattern");
        setPattern(new boolean[][]{{true, true, true}});
    }
}

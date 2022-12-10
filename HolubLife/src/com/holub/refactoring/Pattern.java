package com.holub.refactoring;

public abstract class Pattern {

    private String name;
    private boolean[][] pattern;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[][] getPattern() {
        return this.pattern;
    }

    public void setPattern(boolean[][] pattern) {
        this.pattern = pattern;
    }
}

package com.holub.refactoring;

public abstract class Pattern {
	private String name;
	private Boolean[][] pattern;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPattern(Boolean[][] pattern) {
		this.pattern = pattern;
	}
	
	public Boolean[][] getPattern() {
		return this.pattern;
	}
}

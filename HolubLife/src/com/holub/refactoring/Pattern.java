package com.holub.refactoring;

public abstract class Pattern {
	private String name;
	private Boolean[][] pattern;
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public String GetName() {
		return this.name;
	}
	
	public void SetPattern(Boolean[][] pattern) {
		this.pattern = pattern;
	}
	
	public Boolean[][] GetPattern() {
		return this.pattern;
	}
}

package com.jackbillings.weather;

public enum PressureChange {
	STEADY("steady"),
	RISING("rising"),
	FALLING("falling");
	
	private String name;
	
	private PressureChange(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}

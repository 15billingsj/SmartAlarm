package com.jackbillings.weather;

public enum City {
	ACTON("Acton"),
	SARANAC_LAKE("Saranac Lake"),
	ST_LOUIS("St. Louis");
	
	private String name;
	
	City(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}

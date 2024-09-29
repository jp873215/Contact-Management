package com.ex.enums;

public enum Enums {
	email("email"),
	name("name"),
	UID("UID");
	
	
	private final String value;
	
	Enums(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}

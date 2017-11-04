package ru.romanzes.kammerer.entities;

public class Constant implements Expression {
	private int value;
	
	public Constant(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}

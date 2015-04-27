package com.jackbillings.io;

public interface Io {
	public void configureForOutput(int pinNumber);
	public void on(int pinId);
	public void on(int pinId, long milliseconds);
	public void off(int pinId);
	
	public void configureForInput(int pinNumber, InputPinListener listener);
}

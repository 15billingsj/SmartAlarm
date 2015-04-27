package com.jackbillings.io;

public class IoMorseCode {
	private IoMorseCodePrimitive morseCodePrimitive;
	
	public IoMorseCode(Io io, int pinId) {
		morseCodePrimitive = new IoMorseCodePrimitive(io, pinId);
	}
	
	public void sos() {
		morseCodePrimitive.dot();
		morseCodePrimitive.dot();
		morseCodePrimitive.dot();
		morseCodePrimitive.dash();
		morseCodePrimitive.dash();
		morseCodePrimitive.dash();
		morseCodePrimitive.dot();
		morseCodePrimitive.dot();
		morseCodePrimitive.dot();
	}
}

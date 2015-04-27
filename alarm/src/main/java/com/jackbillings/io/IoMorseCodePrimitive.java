package com.jackbillings.io;

public class IoMorseCodePrimitive {
	private static final long DOT_MILLISECONDS = 250L;
	private static final long DASH_MILLISECONDS = 1500L;
	private static final long PAUSE_MILLISECONDS = 250L;
	private Io io;
	private int pinId;
	
	public IoMorseCodePrimitive(Io io, int pinId) {
		this.io = io;
		this.pinId = pinId;
	}
	
	public void dot() {
		io.on(pinId, DOT_MILLISECONDS);
		pause();
	}
	
	public void dash() {
		io.on(pinId, DASH_MILLISECONDS);
		pause();
	}
	
	private void pause() {
		try {
			Thread.sleep(PAUSE_MILLISECONDS);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

}

package com.jackbillings.io;

public class MockInputPinListener implements InputPinListener {

	public void onPinLowToHigh() {
		System.out.println("Input pin went from low to high");
	}

	public void onPinHighToLow() {
		System.out.println("Input pin went from high to low");
	}

}

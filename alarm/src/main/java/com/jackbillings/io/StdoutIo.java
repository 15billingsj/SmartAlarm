package com.jackbillings.io;

import org.apache.log4j.Logger;

import com.jackbillings.alarm.Alarm;

public class StdoutIo implements Io {
	final static Logger logger = Logger.getLogger(StdoutIo.class);

	public void configureForOutput(int pinNumber) {
		logger.info("Configuring pin " + pinNumber + " for output.");
	}
	public void on(int pinId) {
		logger.info("Pin " + pinId + " ON");
	}

	public void on(int pinId, long milliseconds) {
		System.out.print("Pin " + pinId + " ON for " + milliseconds + " ms... ");
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			logger.info("Sleep interrupted");
		}
		logger.info("Pin " + pinId + " OFF");
	}

	public void off(int pinId) {
		logger.info("Pin " + pinId + " OFF");
	}
	public void configureForInput(int pinNumber, InputPinListener listener) {
		logger.info("Configuring pin " + pinNumber + " for input.");
		
	}
	public boolean waitForChange(int pinNumber) {
		logger.info("Wait for change called for " + pinNumber + ": Not implemented");
		return false;
	}
	
	public boolean isOn(int pinNumber) {
		boolean pinState = true;
		logger.info("isOn() called for " + pinNumber + ": " + pinState);
		return true;
	}

}

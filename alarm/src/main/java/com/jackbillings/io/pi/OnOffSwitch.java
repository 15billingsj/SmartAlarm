package com.jackbillings.io.pi;

import org.apache.log4j.Logger;

import com.jackbillings.io.InputPinListener;
import com.jackbillings.io.Io;
import com.jackbillings.io.StdoutIo;
import com.jackbillings.io.pi.ButtonListener;

public class OnOffSwitch {
	final static Logger logger = Logger.getLogger(OnOffSwitch.class);
	public static final int INPUT_PIN = 24;
	public static final int OUTPUT_PIN = 23;
	private static final String CONFIG_FILE_PATH = "/home/pi/alarm/alarm.properties";
	
	/**
	 * The main method just configures everything.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		// Construct an IO object (it knows how to configure and control GPIO pins)
		Io io = new PiIo(); 
		
		// Construct an object that will listen for button events
		InputPinListener buttonListener = new ButtonListener(io, OUTPUT_PIN, CONFIG_FILE_PATH);
		
		// Configure INPUT_PIN for input, telling it to send messages
		// to buttonListener when button events occur
		io.configureForInput(INPUT_PIN, buttonListener);
		
		// Configure OUTPUT_PIN for output. That pin should be connected (through a resistor)
		// to an LED
		io.configureForOutput(OUTPUT_PIN);
		
		// Although this thread's job (setting everything up) is done, if it exits
		// too quickly, it seems to kill everything, and the listener never gets invoked.
		// Not sure why. But a 30 second sleep here solves the problem.
		
		while(true){
			Thread.sleep(30 * 1000L);
		}
		
		// buttonListener will get invoked (in a different thread, actually each in a
		// separate thread) whenever a button event
		// occurs. So, the real work happens in buttonListener.
		//logger.info("Mainline done");
	}
}

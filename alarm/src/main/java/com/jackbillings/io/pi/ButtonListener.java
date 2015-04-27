package com.jackbillings.io.pi;

import org.apache.log4j.Logger;

import com.jackbillings.alarm.Alarm;
import com.jackbillings.alarm.AlarmConfig;
import com.jackbillings.io.InputPinListener;
import com.jackbillings.io.Io;

public class ButtonListener implements InputPinListener {
	final static Logger logger = Logger.getLogger(ButtonListener.class);
	private Io io;
	private int ledPin;
	private String configFilePath;

	
	private boolean ledIsOn = false; // You might want to use a variable like this
									 // to keep track of whether the LED is on or off
	
	/**
	 * Assuming you've "connected" an input pin to a ButtonListener object, that
	 * ButtonListener object will get invoked (via either the onPinLowToHigh()
	 * or the onPinHighToLow() method) whenever the button attached to the
	 * input pin is pressed / released.
	 * @param io
	 * @param ledPin
	 */
	public ButtonListener(Io io, int ledPin, String configFilePath) {
		this.io = io;
		this.ledPin = ledPin;
		this.configFilePath = configFilePath;
	}

	/**
	 * This method will get called when the button is pressed down.
	 * To the GPIO input pin, the input signal goes "high" (to 3.3 volts).
	 */
	public void onPinLowToHigh(){
		logger.debug("button pressed");
		AlarmConfig config = new AlarmConfig(configFilePath);
		if(ledIsOn){
			io.off(ledPin);
			ledIsOn = false;
			config.setOn(false);
			logger.debug("led turned off");
		}
		else{
			io.on(ledPin);
			ledIsOn = true;
			config.setOn(true);
			logger.debug("led turned on");
		}
		try{
			config.save(configFilePath);
		}
		catch(Exception e){
			logger.error("configFile save failed to " + configFilePath + "; " + e.getMessage(),e);
		}
		// You've got an IO object (io), and you know which pin the LED is connected to (ledPin)... 
		// you could use the io object's methods to turn the LED on or off.
	}
	
	/**
	 * This method will get called when the button is released.
	 * To the GPIO input pin, the input signal goes "low" (to 0 volts).
	 */
	public void onPinHighToLow() {
		logger.debug("button released");
	}
}

package com.jackbillings.io.pi;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jackbillings.io.InputPinListener;
import com.jackbillings.io.Io;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
/**
 * Raspberry Pi IO 
 * @author sbillings
 * 
 * TODO: Implement input pins: http://pi4j.com/example/listener.html 
 *
 */
public class PiIo implements Io {
	final static Logger logger = Logger.getLogger(PiIo.class);
	final GpioController gpioController;
	Map<Integer, GpioPinDigitalOutput> outputPins = new HashMap<Integer, GpioPinDigitalOutput>();
	Map<Integer, GpioPinDigitalInput> inputPins = new HashMap<Integer, GpioPinDigitalInput>();
	Map<Integer, PiIoInputListener> inputListeners = new HashMap<Integer, PiIoInputListener>();
	
	private class PiIoInputListener implements GpioPinListenerDigital {
		private boolean eventReceived=false;
		private InputPinListener userListener;
		
		public PiIoInputListener(InputPinListener userListener) {
			this.userListener = userListener;
		}
		
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)  {
        	eventReceived = true;
            // display pin state on console
        	PinState pinState = event.getState();
            logger.info(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + pinState);
            if (pinState == PinState.HIGH) {
            	userListener.onPinLowToHigh();
            } else {
            	userListener.onPinHighToLow();
            }
        }
        public boolean isEventReceived() {
        	return eventReceived;
        }
    }

	public PiIo() {
		gpioController = GpioFactory.getInstance();
	}
	
	public void configureForOutput(int pinNumber) {
		logger.info("Configuring for output pin " + pinNumber);
		GpioPinDigitalOutput outputPin = 
				gpioController.provisionDigitalOutputPin(getPin(pinNumber), "Pin" + pinNumber, PinState.LOW);
		outputPins.put(pinNumber, outputPin);
	}
	
	public void configureForInput(int pinNumber, InputPinListener genericListener) {
		logger.info("Configuring for input pin " + pinNumber);
		GpioPinDigitalInput inputPin = 
				gpioController.provisionDigitalInputPin(getPin(pinNumber), PinPullResistance.PULL_DOWN);
		PiIoInputListener piListener = new PiIoInputListener(genericListener);
		inputPin.addListener(piListener);
		inputPins.put(pinNumber, inputPin);
		inputListeners.put(pinNumber, piListener);
	}

	public void on(int pinNumber) {
		logger.info("Turning on pin " + pinNumber);
		GpioPinDigitalOutput pin = outputPins.get(pinNumber);
		pin.high();
	}

	public void on(int pinId, long milliseconds) {
		on(pinId);
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
		}
		off(pinId);
	}

	public void off(int pinNumber) {
		logger.info("Turning off pin " + pinNumber);
		GpioPinDigitalOutput pin = outputPins.get(pinNumber);
		pin.low();
	}
	
	private Pin getPin(int pinNumber) {
		switch (pinNumber) {
		case 17:
			return RaspiPin.GPIO_00;
		case 18:
			return RaspiPin.GPIO_01;
		case 27:
			return RaspiPin.GPIO_02;
		case 22:
			return RaspiPin.GPIO_03;
		case 23:
			return RaspiPin.GPIO_04;
		case 24:
			return RaspiPin.GPIO_05;
		case 25:
			return RaspiPin.GPIO_06;
		case 4:
			return RaspiPin.GPIO_07;
		default:
			throw new UnsupportedOperationException("Pin # " + pinNumber + " not supported");
		}
	}
}

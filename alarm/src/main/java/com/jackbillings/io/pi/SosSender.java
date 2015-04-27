package com.jackbillings.io.pi;

import com.jackbillings.io.Io;
import com.jackbillings.io.IoMorseCode;

public class SosSender {

	public static void main(String[] args) throws InterruptedException {
		Io io = new PiIo();
		io.configureForOutput(23);
		IoMorseCode mc = new IoMorseCode(io, 23);
		
		for (int i=0; i < 5; i++) {
			mc.sos();
			
			Thread.sleep(2000L);
		}
	}

}

package com.jackbillings.io;

import org.junit.Test;

import com.jackbillings.io.Io;
import com.jackbillings.io.IoMorseCode;
import com.jackbillings.io.StdoutIo;

public class IoMorseCodeTest {

	@Test
	public void testSos() {
		Io io = new StdoutIo();
		IoMorseCode mc = new IoMorseCode(io, 1);
		mc.sos();
	}

}

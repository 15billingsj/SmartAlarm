package com.jackbillings.sound.audiofile;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AudioFileSoundPlayerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		System.out.println("Constructing AudioFileSoundPlayer");
		AudioFileSoundPlayer player = new AudioFileSoundPlayer("police_s.wav");
		System.out.println("Playing sound for 7 seconds using .playSound() method");
		player.playSound(7);
		System.out.println("Done");
		
		System.out.println("Playing sound for 5 seconds using .startPlayingSound() and .stopPlayingSound() methods");
		player.startPlayingSound();
		Thread.sleep(5000L);
		player.stopPlayingSound();
		System.out.println("Done");
	}

}

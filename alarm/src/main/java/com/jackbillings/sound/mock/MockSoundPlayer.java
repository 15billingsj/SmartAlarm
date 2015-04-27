package com.jackbillings.sound.mock;

import com.jackbillings.sound.SoundPlayer;

public class MockSoundPlayer implements SoundPlayer {
	private boolean played=false; // Tracks whether or not the sound was played

	public void playSound(int numSeconds) throws Exception {
		played = true;
	}

	public boolean wasPlayed() {
		boolean answer = played;
		played = false; // reset
		return answer;
	}
}

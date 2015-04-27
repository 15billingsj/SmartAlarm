package com.jackbillings.sound.audiofile;

import java.io.*;
import org.apache.log4j.Logger;
import com.jackbillings.sound.SoundPlayer;

import sun.audio.*;

public class AudioFileSoundPlayer implements SoundPlayer {
	final static Logger logger = Logger.getLogger(AudioFileSoundPlayer.class);
	private String filename;
	private ContinuousAudioDataStream activeAudioDataStream = null;
	private AudioStream audioStream = null;
	
	public static void main(String[] args) throws Exception {
		logger.info("Constructing AudioFileSoundPlayer");
		AudioFileSoundPlayer player = new AudioFileSoundPlayer("police_s.wav");
		logger.info("Playing sound");
		player.playSound(5);
		logger.info("Done");
	}
	
	public AudioFileSoundPlayer(String filename) {
		this.filename = filename;
	}
		
	public void playSound(int numSeconds) throws FileNotFoundException, IOException {
		startPlayingSound();
		logger.info("Sleeping for " + numSeconds + " seconds");
		try {
			Thread.sleep(numSeconds*1000L);
		} catch (InterruptedException e) {
			logger.warn("Sleep interrupted");
		}
		logger.info("Sleep is done; stopping audio player");
		stopPlayingSound();
	}
	
	public void startPlayingSound() throws FileNotFoundException, IOException {
		// open the sound file as a Java input stream
		InputStream in;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			logger.info("File " + filename + " not found: " + e.getMessage());
			throw e;
		}

		// create an audiostream from the inputstream
		try {
			audioStream = new AudioStream(in);
		} catch (IOException e) {
			logger.info("IOException constructing AudioStream for file " + filename +
					": " + e.getMessage());
			throw e;
		}

		// play the audio clip with the audioplayer class
		try {
			AudioData data = audioStream.getData();
			activeAudioDataStream = new ContinuousAudioDataStream (data);
			
			logger.info("Starting audio player");
			AudioPlayer.player.start(activeAudioDataStream);
			logger.info("Audio player started");
		} catch (Throwable e) {
			logger.info("Exception starting audio for file " + filename +
					": " + e.getMessage());
		}
	}
	
	public void stopPlayingSound() throws FileNotFoundException, IOException {
		try {
			logger.info("Stopping audio player");
			AudioPlayer.player.stop(activeAudioDataStream);
			activeAudioDataStream.close();
			activeAudioDataStream = null;
			if (audioStream != null) {
				audioStream.close();
				audioStream = null;
			}
			logger.info("Audio player stopped");
		} catch (Throwable e) {
			logger.info("Exception stopping audio for file " + filename +
					": " + e.getMessage());
		}
	}
}

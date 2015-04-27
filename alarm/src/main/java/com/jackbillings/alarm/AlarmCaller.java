package com.jackbillings.alarm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.jackbillings.sound.SoundPlayer;
import com.jackbillings.sound.audiofile.AudioFileSoundPlayer;
import com.jackbillings.weather.City;
import com.jackbillings.weather.WeatherDao;
import com.jackbillings.weather.WeatherPojo;
import com.jackbillings.weather.yahoo.YahooWeatherDao;

public class AlarmCaller {
	final static Logger logger = Logger.getLogger(AlarmCaller.class);
	private static final String CONFIG_FILE_PATH = "/home/pi/alarm/alarm.properties";

	
	public static void main(String[] args){
		Calendar currentTime = new GregorianCalendar();
		AlarmConfig config = new AlarmConfig(CONFIG_FILE_PATH);
		SoundPlayer soundPlayer = new AudioFileSoundPlayer("police_s.wav");
		City city = City.ACTON; // **MAKE THIS A VARIABLE LATER
		WeatherDao weatherDao = new YahooWeatherDao();
		weatherDao.setCity(city);
		WeatherPojo currentWeather = null;
		try{
			currentWeather = weatherDao.getWeather();
		}
		catch(Exception e){
			logger.error("error getting weather; " + e.getMessage(),e);
			System.exit(-1);
		}
		Alarm alarm = new Alarm(config, currentTime, currentWeather, soundPlayer);
		try{
			alarm.soundAlarmIfConditionsAreRight();
		}
		catch(Exception e){
			logger.error("error sounding alarm; " + e.getMessage(),e);
			System.exit(-1);
		}
	}
}

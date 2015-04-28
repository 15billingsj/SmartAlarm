package com.jackbillings.alarm;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.jackbillings.sound.SoundPlayer;
import com.jackbillings.weather.WeatherPojo;

public class Alarm {
	final static Logger logger = Logger.getLogger(Alarm.class);
	private AlarmConfig config;
	private Calendar currentTime;
	private WeatherPojo currentWeather;
	private SoundPlayer soundPlayer;

	public Alarm(AlarmConfig config, Calendar currentTime,
			WeatherPojo currentWeather, SoundPlayer soundPlayer) {
		this.config = config;
		this.currentTime = currentTime;
		this.currentWeather = currentWeather;
		this.soundPlayer = soundPlayer;
	}

	private boolean isTimeToWakeUp() {
		if(!config.isOn()) return false;
		boolean decision = false;
		int hour = currentTime.get(Calendar.HOUR_OF_DAY); // hour, using 24 hour clock
		int minute = currentTime.get(Calendar.MINUTE);
		if(config.getWakeTimeHours() == hour && Math.abs(config.getWakeTimeMinutes() - minute) < 5){
			if(weatherIsGood()) decision = true;
		}

		logger.info("Alarm.shouldWakeUp():" + 
				"\nconfig: " + config.toString() +
				"\ncurrentTime: " + currentTime.getTime().toString() +
				"\ncurrentWeather: " + currentWeather.toString());
		
		logger.info("Returning: " + decision + "\n");
		return decision;
	}
	
	private boolean weatherIsGood(){
		WeatherPojo w = currentWeather;
		
		if (config.isChanceOfPrecipitationRelevant()) {
			if ((w.getChanceOfPrecipitation() < config.getChanceOfPrecipitationMin()) || (w.getChanceOfPrecipitation() > config.getChanceOfPrecipitationMax())) {
				logger.debug("Chance of Precipitation is out of range");
				return false;
			}
		}
		
		if (config.isTemperatureRelevant()) {
			if ((w.getTemperature() < config.getTemperatureMin()) || (w.getTemperature() > config.getTemperatureMax())) {
				logger.debug("Temperature is out of range");
				return false;
			}
		}
		
		if (config.isHumidityRelevant()) {
			if ((w.getHumidity() < config.getHumidityMin()) || (w.getHumidity() > config.getHumidityMax())) {
				logger.debug("Humidity is out of range");
				return false;
			}
		}
		
		if (config.isWindDirectionRelevant()) {
			if ((w.getWindDirection() < config.getWindDirectionMin()) || (w.getWindDirection() > config.getWindDirectionMax())) {
				logger.debug("Wind Direction is out of range");
				return false;
			}
		}
		
		if (config.isWindSpeedRelevant()) {
			if ((w.getWindSpeed() < config.getWindSpeedMin()) || (w.getWindSpeed() > config.getWindSpeedMax())) {
				logger.debug("Wind Speed is out of range");
				return false;
			}
		}
		
		logger.debug("Weather is good");
		return true;
	}
	
	public void soundAlarmIfConditionsAreRight() throws Exception {
		if (isTimeToWakeUp()) {
			soundPlayer.playSound(10);
		}
	}
}

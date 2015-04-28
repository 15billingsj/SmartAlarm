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
		if((currentWeather.getChanceOfPrecipitation() < config.getChanceOfPrecipitationMin()
				|| currentWeather.getChanceOfPrecipitation() > config.getChanceOfPrecipitationMax()
				|| currentWeather.getTemperature() < config.getTemperatureMin()
				|| currentWeather.getTemperature() > config.getTemperatureMax()
				|| currentWeather.getHumidity() < config.getHumidityMin()
				|| currentWeather.getHumidity() > config.getHumidityMax()
				|| currentWeather.getWindDirection() < config.getWindDirectionMin()
				|| currentWeather.getWindDirection() > config.getWindDirectionMax()
				|| currentWeather.getWindSpeed() < config.getWindSpeedMin()
				|| currentWeather.getWindSpeed() > config.getWindSpeedMax())
				&& config.isChanceOfPrecipitationRelevant()
				&& config.isHumidityRelevant()
				&& config.isTemperatureRelevant()
				&& config.isWindDirectionRelevant()
				&& config.isWindSpeedRelevant()){
			logger.debug("weather is bad");
			return false;
		}
		//add all other checks (current weather against required weather)
		logger.debug("weather is good");
		return true;
	}
	
	public void soundAlarmIfConditionsAreRight() throws Exception {
		if (isTimeToWakeUp()) {
			soundPlayer.playSound(10);
		}
	}
}

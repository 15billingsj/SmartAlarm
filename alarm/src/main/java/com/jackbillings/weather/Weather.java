package com.jackbillings.weather;

import org.apache.log4j.Logger;

import com.jackbillings.sound.audiofile.AudioFileSoundPlayer;
import com.jackbillings.weather.yahoo.YahooWeatherDao;

public class Weather {
	final static Logger logger = Logger.getLogger(Weather.class);

	public static void main(String[] args) throws Exception {
		City city = City.ST_LOUIS;
		WeatherDao weatherDao = new YahooWeatherDao();
		weatherDao.setCity(city);
		WeatherPojo weather = weatherDao.getWeather();
		logger.info("Weather for " + city.getName() + ": " + weather.toString());
	}

}

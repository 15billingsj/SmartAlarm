package com.jackbillings.weather.mock;

import com.jackbillings.weather.City;
import com.jackbillings.weather.PressureChange;
import com.jackbillings.weather.WeatherDao;
import com.jackbillings.weather.WeatherPojo;

public class MockWeatherDao implements WeatherDao {
	private int temperature;
	private float pressure;
	private PressureChange pressureChange;
	private int windSpeed;
	private int windDirection;
	private int humidity;
	private int chanceOfPrecipitation;

	public MockWeatherDao(int temperature, float pressure,
			PressureChange pressureChange, int windSpeed, int windDirection,
			int humidity, int chanceOfPrecipitation) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.pressureChange = pressureChange;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.humidity = humidity;
		this.chanceOfPrecipitation = chanceOfPrecipitation;
	}

	public void setCity(City city) {
	}

	public WeatherPojo getWeather() throws Exception {
		WeatherPojo weather = new WeatherPojo(temperature, pressure, pressureChange,
				windSpeed, windDirection, humidity, chanceOfPrecipitation);
		return weather;
	}

}

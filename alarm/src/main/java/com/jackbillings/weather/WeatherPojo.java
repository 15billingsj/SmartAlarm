package com.jackbillings.weather;

import java.util.Date;

public class WeatherPojo {

	private int temperature; // Fahrenheit
	private float pressure; // inches
	private PressureChange pressureChange; // STEADY, RISING, FALLING
	private int windSpeed; // mph
	private int windDirection; // degrees
	private int humidity; // percent
	private int chanceOfPrecipitation; // percentage
	
	public WeatherPojo(int temperature, float pressure, PressureChange pressureChange,
			int windSpeed, int windDirection, int humidity, int chanceOfPrecipitation) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.pressureChange = pressureChange;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.humidity = humidity;
		this.chanceOfPrecipitation = chanceOfPrecipitation;
	}
	public int getTemperature() {
		return temperature;
	}

	public float getPressure() {
		return pressure;
	}

	public PressureChange getPressureChange() {
		return pressureChange;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public int getHumidity() {
		return humidity;
	}
	
	
	public int getChanceOfPrecipitation() {
		return chanceOfPrecipitation;
	}
	@Override
	public String toString() {
		return "WeatherPojo [temperature=" + temperature + ", pressure="
				+ pressure + ", pressureChange=" + pressureChange
				+ ", windSpeed=" + windSpeed + ", windDirection="
				+ windDirection + ", humidity=" + humidity
				+ ", chanceOfPrecipitation=" + chanceOfPrecipitation + "]";
	}
}

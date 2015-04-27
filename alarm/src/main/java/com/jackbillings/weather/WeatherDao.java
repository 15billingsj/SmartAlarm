package com.jackbillings.weather;

public interface WeatherDao {
	public void setCity(City city);
	public WeatherPojo getWeather() throws Exception ;
}

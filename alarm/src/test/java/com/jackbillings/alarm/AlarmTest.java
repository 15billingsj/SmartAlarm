package com.jackbillings.alarm;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jackbillings.sound.mock.MockSoundPlayer;
import com.jackbillings.weather.PressureChange;
import com.jackbillings.weather.WeatherDao;
import com.jackbillings.weather.WeatherPojo;
import com.jackbillings.weather.mock.MockWeatherDao;

public class AlarmTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testTimeBadWeatherGood() throws Exception {
		
		// Create some gorgeous weather
		// A WeatherDao handles the messy job of getting
		// weather details from a particular weather service,
		// and then returns those weather details (in the form
		// of a WeatherPojo) when asked.
		// DAO = Data Access Object.
		// Real WeatherDao's will get actual weather details from
		// services like Yahoo, but the MockWeatherDao lets
		// you tell it what weather to return when asked.
		// [To construct a real weather DAO,
		// you will call the real Weather DAO's constructor with no arguments.
		int temperature = 80;
		float pressure = 30.5f;
		PressureChange pressureChange = PressureChange.RISING;
		int windSpeed = 0;
		int windDirection = 0;
		int humidity = 10;
		int chanceOfPrecipitation = 0;
		WeatherDao weatherDao = new MockWeatherDao(temperature, pressure,
				pressureChange, windSpeed, windDirection,
				humidity, chanceOfPrecipitation);
		
		// A WeatherPojo contains weather details in a generic
		// (weather-service-independent) way, so you can write
		// code that will work with any weather service (as long
		// as you can get a DAO for it). WeatherDao's know
		// how to create WeatherPojo's.
		WeatherPojo currentWeather = weatherDao.getWeather();
		
		// An AlarmConfig object stores the alarm settings
		// (what time to wake if the weather is good,
		// and what is considered good weather, and
		// the sound to play). By default, it'll wake
		// at 6am, regardless of weather.
		AlarmConfig config = new AlarmConfig();
		config.setWakeTimeHours(6); // 6am
		config.setWakeTimeMinutes(0);
		config.setChanceOfPrecipitationRelevant(true);
		config.setChanceOfPrecipitationMax(60); // good weather only
		config.setOn(true);
		
		
		// Now, create the test (fake) scenario:
		// Fake the "current" time = 5:55am (too early)
		// [To construct the real (current) time, you
		// will call the GregorianCalendar constructor
		// with no arguments.]
		int year = 2014;
		int month = 8;
		int dayOfMonth = 15;
		int hourOfDay = 5; // BEFORE the wake time we set above
		int minute = 55;
		Calendar currentTime = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);

		// Create a mock sound player. Afterward, we can ask it
		// whether Alarm asked it to sound the alarm.
		// [The real sound player (AudioFileSoundPlayer) is used
		// the same way, except that you can't ask it whether or not its alarm
		// was sounded.]
		MockSoundPlayer soundPlayer = new MockSoundPlayer();
		
		// Create the alarm clock object
		Alarm alarm = new Alarm(config, currentTime, currentWeather, soundPlayer);
		
		// Finally: test the alarm
		alarm.soundAlarmIfConditionsAreRight();

		// It's before the wake up time. Regardless of weather: it should NOT wake the guy up
		// [In your actual alarm code, if shouldWakeUp() returns true, you
		// will use SoundPlayer to sound the alarm.] 
		assertFalse("Alarm went off, but it's too early", 
				soundPlayer.wasPlayed());
	}

	@Test
	public void testTimeGoodWeatherGood() throws Exception {
		
		// Create some decent weather
		int temperature = 80;
		float pressure = 30.5f;
		PressureChange pressureChange = PressureChange.RISING;
		int windSpeed = 0;
		int windDirection = 0;
		int humidity = 10;
		int chanceOfPrecipitation = 50;
		
		WeatherDao weatherDao = new MockWeatherDao(temperature, pressure,
				pressureChange, windSpeed, windDirection,
				humidity, chanceOfPrecipitation);
		
		WeatherPojo currentWeather = weatherDao.getWeather();
		
		// Configure to wake at 6:00am as long as < 60% chance of rain
		AlarmConfig config = new AlarmConfig();
		config.setWakeTimeHours(6); // 6am
		config.setWakeTimeMinutes(0);
		config.setChanceOfPrecipitationRelevant(true);
		config.setChanceOfPrecipitationMax(60);
		config.setOn(true);
		
		// Fake the "current" time = 6:01am (so yes, alarm should go off)
		int year = 2014;
		int month = 8;
		int dayOfMonth = 15;
		int hourOfDay = 6;
		int minute = 1;
		Calendar currentTime = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);

		MockSoundPlayer soundPlayer = new MockSoundPlayer();
		
		// Create the alarm clock object
		Alarm alarm = new Alarm(config, currentTime, currentWeather, soundPlayer);

		// Finally: test the alarm
		alarm.soundAlarmIfConditionsAreRight();
				
		// It's after the wake up time, weather is gorgeous: it should wake the guy up
		assertTrue("Alarm didn't go off, but it's time and the weather is good",
				soundPlayer.wasPlayed());
	}
	
	@Test
	public void testTimeGoodWeatherBad() throws Exception {
		
		// Create some terrible weather
		int temperature = 55;
		float pressure = 29.0f;
		PressureChange pressureChange = PressureChange.FALLING;
		int windSpeed = 20;
		int windDirection = 0;
		int humidity = 100;
		int chanceOfPrecipitation = 100;
		
		WeatherDao weatherDao = new MockWeatherDao(temperature, pressure,
				pressureChange, windSpeed, windDirection,
				humidity, chanceOfPrecipitation);
		
		WeatherPojo currentWeather = weatherDao.getWeather();
		
		// Configure alarm to only go off if weather is good
		AlarmConfig config = new AlarmConfig();
		config.setWakeTimeHours(6); // 6am
		config.setWakeTimeMinutes(0);
		config.setChanceOfPrecipitationRelevant(true);
		config.setChanceOfPrecipitationMax(60);
		config.setOn(true);
		
		// Fake the "current" time = 6:01am (if weather were good: alarm should go off)
		int year = 2014;
		int month = 8;
		int dayOfMonth = 15;
		int hourOfDay = 6;
		int minute = 1;
		Calendar currentTime = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);

		MockSoundPlayer soundPlayer = new MockSoundPlayer();
		
		// Create the alarm clock object
		Alarm alarm = new Alarm(config, currentTime, currentWeather, soundPlayer);

		// Finally: test the alarm
		alarm.soundAlarmIfConditionsAreRight();
				
		// It's after the wake up time, but the weather is awful: it should NOT wake the guy up
		assertFalse("Alarm went off, but is is raining", soundPlayer.wasPlayed());
	}

}

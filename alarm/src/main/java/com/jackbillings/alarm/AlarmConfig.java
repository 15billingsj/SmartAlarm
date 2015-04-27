package com.jackbillings.alarm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jackbillings.weather.PressureChange;

public class AlarmConfig {
	private static final String ON_PROPERTY = "on";
	private static final String CHANCE_OF_PRECIPITATION_MAX_PROPERTY = "chance.of.precipitation.max";
	private static final String CHANCE_OF_PRECIPITATION_MIN_PROPERTY = "chance.of.precipitation.min";
	private static final String CHANCE_OF_PRECIPITATION_RELEVANT = "chance.of.precipitation.relevant";
	private static final String HUMIDITY_MAX = "humidity.max";
	private static final String HUMIDITY_MIN = "humidity.min";
	private static final String HUMIDITY_RELEVANT_PROPERTY = "humidity.relevant";
	private static final String WIND_DIRECTION_MAX_PROPERTY = "wind.direction.max";
	private static final String WIND_DIRECTION_MIN_PROPERTY = "wind.direction.min";
	private static final String WIND_DIRECTION_RELEVANT_PROPERTY = "wind.direction.relevant";
	private static final String WIND_SPEED_MAX_PROPERTY = "wind.speed.max";
	private static final String WIND_SPEED_MIN_PROPERTY = "wind.speed.min";
	private static final String WIND_SPEED_RELEVANT_PROPERTY = "wind.speed.relevant";
	private static final String PRESSURE_CHANGE_MAX_PROPERTY = "pressure.change.max";
	private static final String PRESSURE_CHANGE_MIN_PROPERTY = "pressure.change.min";
	private static final String PRESSURE_CHANGE_RELEVANT_PROPERTY = "pressure.change.relevant";
	private static final String PRESSURE_MAX_PROPERTY = "pressure.max";
	private static final String PRESSURE_MIN_PROPERTY = "pressure.min";
	private static final String PRESSURE_RELEVANT_PROPERTY = "pressure.relevant";
	private static final String TEMPERATURE_MAX_PROPERTY = "temperature.max";
	private static final String TEMPERATURE_MIN_PROPERTY = "temperature.min";
	private static final String TEMPERATURE_RELEVANT_PROPERTY = "temperature.relevant";
	private static final String WAKE_TIME_MINUTES_PROPERTY = "wake.time.minutes";
	private static final String WAKE_TIME_HOURS_PROPERTY = "wake.time.hours";
	private int wakeTimeHours = 6; // 24 hour clock; 0 and 24 equal midnight; 13 equals 1pm, etc.
	private int wakeTimeMinutes = 0;
	
	private boolean on = false;
	
	private boolean temperatureRelevant = false;
	private int temperatureMin = -1000;
	private int temperatureMax = 1000;

	private boolean pressureRelevant = false;
	private float pressureMin = 0.0f;
	private float pressureMax = 1000.0f;

	private boolean pressureChangeRelevant = false;
	private PressureChange pressureChangeMin = PressureChange.FALLING;
	private PressureChange pressureChangeMax = PressureChange.RISING;

	private boolean windSpeedRelevant = false;
	private int windSpeedMin = 0;
	private int windSpeedMax = 1000;

	private boolean windDirectionRelevant = false;
	private int windDirectionMin = 0;
	private int windDirectionMax = 360;

	private boolean humidityRelevant = false;
	private int humidityMin = 0;
	private int humidityMax = 100;

	private boolean chanceOfPrecipitationRelevant = false;
	private int chanceOfPrecipitationMin = 0;
	private int chanceOfPrecipitationMax = 100;

	/**
	 * If you use this constructor, be sure to use the setters to set some reasonable values.
	 */
	public AlarmConfig() {
	}
	
	/**
	 * Initialize the configuration from a properties file
	 * @param filename
	 * @throws IOException
	 */
	public AlarmConfig(String filename) {
		Properties props;
		try {
			props = loadPropertiesFromFile(filename);
		} catch (IOException e) {
			props = new Properties();
		}
		init(props);
	}
	
	
	
	private void init(Properties props) {
		on = getBoolean(props, ON_PROPERTY, false);
		
		wakeTimeHours = getInt(props, WAKE_TIME_HOURS_PROPERTY, 6);
		wakeTimeMinutes = getInt(props, WAKE_TIME_MINUTES_PROPERTY, 0);
		
		temperatureRelevant = getBoolean(props, TEMPERATURE_RELEVANT_PROPERTY, false);
		temperatureMin = getInt(props, TEMPERATURE_MIN_PROPERTY, -1000);
		temperatureMax = getInt(props, TEMPERATURE_MAX_PROPERTY, 1000);
		
		pressureRelevant = getBoolean(props, PRESSURE_RELEVANT_PROPERTY, false);
		pressureMin = getFloat(props, PRESSURE_MIN_PROPERTY, 0.0f);
		pressureMax = getFloat(props, PRESSURE_MAX_PROPERTY, 100.0f);
		
		pressureChangeRelevant = getBoolean(props, PRESSURE_CHANGE_RELEVANT_PROPERTY, false);
		pressureChangeMin = getPressureChange(props, PRESSURE_CHANGE_MIN_PROPERTY, PressureChange.FALLING);
		pressureChangeMax = getPressureChange(props, PRESSURE_CHANGE_MAX_PROPERTY, PressureChange.RISING);
		
		windSpeedRelevant = getBoolean(props, WIND_SPEED_RELEVANT_PROPERTY, false);
		windSpeedMin = getInt(props, WIND_SPEED_MIN_PROPERTY, 0);
		windSpeedMax = getInt(props, WIND_SPEED_MAX_PROPERTY, 1000);
		
		windDirectionRelevant = getBoolean(props, WIND_DIRECTION_RELEVANT_PROPERTY, false);
		windDirectionMin = getInt(props, WIND_DIRECTION_MIN_PROPERTY, 0);
		windDirectionMax = getInt(props, WIND_DIRECTION_MAX_PROPERTY, 360);
		
		humidityRelevant = getBoolean(props, HUMIDITY_RELEVANT_PROPERTY, false);
		humidityMin = getInt(props, HUMIDITY_MIN, 0);
		humidityMax = getInt(props, HUMIDITY_MAX, 100);
		
		chanceOfPrecipitationRelevant = getBoolean(props, CHANCE_OF_PRECIPITATION_RELEVANT, false);
		chanceOfPrecipitationMin = getInt(props, CHANCE_OF_PRECIPITATION_MIN_PROPERTY, 0);
		chanceOfPrecipitationMax = getInt(props, CHANCE_OF_PRECIPITATION_MAX_PROPERTY, 100);
	}
	
	public void save(String filename) throws IOException {
		Properties props = new Properties();
		
		props.setProperty(ON_PROPERTY, toString(isOn()));
		props.setProperty(WAKE_TIME_HOURS_PROPERTY, toString(getWakeTimeHours()));
		props.setProperty(WAKE_TIME_MINUTES_PROPERTY, toString(getWakeTimeMinutes()));
		props.setProperty(TEMPERATURE_RELEVANT_PROPERTY, toString(isTemperatureRelevant()));
		props.setProperty(TEMPERATURE_MIN_PROPERTY, toString(getTemperatureMin()));
		props.setProperty(TEMPERATURE_MAX_PROPERTY, toString(getTemperatureMax()));
		props.setProperty(PRESSURE_RELEVANT_PROPERTY, toString(isPressureRelevant()));
		props.setProperty(PRESSURE_MIN_PROPERTY, toString(getPressureMin()));
		props.setProperty(PRESSURE_MAX_PROPERTY, toString(getPressureMax()));
		props.setProperty(PRESSURE_CHANGE_RELEVANT_PROPERTY, toString(isPressureChangeRelevant()));
		props.setProperty(PRESSURE_CHANGE_MIN_PROPERTY, toString(getPressureChangeMin()));
		props.setProperty(PRESSURE_CHANGE_MAX_PROPERTY, toString(getPressureChangeMax()));
		props.setProperty(WIND_SPEED_RELEVANT_PROPERTY, toString(isWindSpeedRelevant()));
		props.setProperty(WIND_SPEED_MIN_PROPERTY, toString(getWindSpeedMin()));
		props.setProperty(WIND_SPEED_MAX_PROPERTY, toString(getWindSpeedMax()));
		props.setProperty(WIND_DIRECTION_RELEVANT_PROPERTY, toString(isWindDirectionRelevant()));
		props.setProperty(WIND_DIRECTION_MIN_PROPERTY, toString(getWindDirectionMin()));
		props.setProperty(WIND_DIRECTION_MAX_PROPERTY, toString(getWindDirectionMax()));
		props.setProperty(HUMIDITY_RELEVANT_PROPERTY, toString(isHumidityRelevant()));
		props.setProperty(HUMIDITY_MIN, toString(getHumidityMin()));
		props.setProperty(HUMIDITY_MAX, toString(getHumidityMax()));
		props.setProperty(CHANCE_OF_PRECIPITATION_RELEVANT, toString(isChanceOfPrecipitationRelevant()));
		props.setProperty(CHANCE_OF_PRECIPITATION_MIN_PROPERTY, toString(getChanceOfPrecipitationMin()));
		props.setProperty(CHANCE_OF_PRECIPITATION_MAX_PROPERTY, toString(getChanceOfPrecipitationMax()));
		
		OutputStream os = new FileOutputStream(filename);
		props.store(os, "Alarm configuration properties");
	}
	
	private String toString(int value) {
		return Integer.toString(value);
	}
	
	private String toString(boolean value) {
		return Boolean.toString(value);
	}
	
	private String toString(float value) {
		return Float.toString(value);
	}
	
	private String toString(PressureChange value) {
		return value.toString();
	}
	
	private int getInt(Properties props, String propertyName, int defaultValue) {
		int value = defaultValue;
		String valueString = props.getProperty(propertyName);
		if (valueString != null) {
			value = Integer.parseInt(valueString);
		}
		return value;
	}
	
	private float getFloat(Properties props, String propertyName, float defaultValue) {
		float value = defaultValue;
		String valueString = props.getProperty(propertyName);
		if (valueString != null) {
			value = Float.parseFloat(valueString);
		}
		return value;
	}
	
	private boolean getBoolean(Properties props, String propertyName, boolean defaultValue) {
		boolean value = defaultValue;
		String valueString = props.getProperty(propertyName);
		if ("true".equalsIgnoreCase(valueString)) {
			value = true;
		} else if ("false".equalsIgnoreCase(valueString)) {
			value = false;
		}
		return value;
	}
	
	private PressureChange getPressureChange(Properties props, String propertyName, PressureChange defaultValue) {
		PressureChange value = defaultValue;
		String valueString = props.getProperty(propertyName);
		if ("steady".equalsIgnoreCase(valueString)) {
			value = PressureChange.STEADY;
		} else if ("rising".equalsIgnoreCase(valueString)) {
			value = PressureChange.RISING;
		} else if ("falling".equalsIgnoreCase(valueString)) {
			value = PressureChange.FALLING;
		}
		return value;
	}

	private Properties loadPropertiesFromFile(String filename) throws IOException {
		Properties props = new Properties();
		
		InputStream is = null;
		try {
			is = new FileInputStream(filename);
			props.load(is);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException closeException) {
					// ignore
				}
			}
		}
		return props;
	}
	
	public boolean isOn(){
		return on;
	}
	
	public void setOn(boolean on){
		this.on = on;
	}

	public int getWakeTimeHours() {
		return wakeTimeHours;
	}

	public void setWakeTimeHours(int wakeTimeHours) {
		this.wakeTimeHours = wakeTimeHours;
	}

	public int getWakeTimeMinutes() {
		return wakeTimeMinutes;
	}

	public void setWakeTimeMinutes(int wakeTimeMinutes) {
		this.wakeTimeMinutes = wakeTimeMinutes;
	}

	public boolean isTemperatureRelevant() {
		return temperatureRelevant;
	}

	public void setTemperatureRelevant(boolean temperatureRelevant) {
		this.temperatureRelevant = temperatureRelevant;
	}

	public int getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(int temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public int getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(int temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public boolean isPressureRelevant() {
		return pressureRelevant;
	}

	public void setPressureRelevant(boolean pressureRelevant) {
		this.pressureRelevant = pressureRelevant;
	}

	public float getPressureMin() {
		return pressureMin;
	}

	public void setPressureMin(float pressureMin) {
		this.pressureMin = pressureMin;
	}

	public float getPressureMax() {
		return pressureMax;
	}

	public void setPressureMax(float pressureMax) {
		this.pressureMax = pressureMax;
	}

	public boolean isPressureChangeRelevant() {
		return pressureChangeRelevant;
	}

	public void setPressureChangeRelevant(boolean pressureChangeRelevant) {
		this.pressureChangeRelevant = pressureChangeRelevant;
	}

	public PressureChange getPressureChangeMin() {
		return pressureChangeMin;
	}

	public void setPressureChangeMin(PressureChange pressureChangeMin) {
		this.pressureChangeMin = pressureChangeMin;
	}

	public PressureChange getPressureChangeMax() {
		return pressureChangeMax;
	}

	public void setPressureChangeMax(PressureChange pressureChangeMax) {
		this.pressureChangeMax = pressureChangeMax;
	}

	public boolean isWindSpeedRelevant() {
		return windSpeedRelevant;
	}

	public void setWindSpeedRelevant(boolean windSpeedRelevant) {
		this.windSpeedRelevant = windSpeedRelevant;
	}

	public int getWindSpeedMin() {
		return windSpeedMin;
	}

	public void setWindSpeedMin(int windSpeedMin) {
		this.windSpeedMin = windSpeedMin;
	}

	public int getWindSpeedMax() {
		return windSpeedMax;
	}

	public void setWindSpeedMax(int windSpeedMax) {
		this.windSpeedMax = windSpeedMax;
	}

	public boolean isWindDirectionRelevant() {
		return windDirectionRelevant;
	}

	public void setWindDirectionRelevant(boolean windDirectionRelevant) {
		this.windDirectionRelevant = windDirectionRelevant;
	}

	public int getWindDirectionMin() {
		return windDirectionMin;
	}

	public void setWindDirectionMin(int windDirectionMin) {
		this.windDirectionMin = windDirectionMin;
	}

	public int getWindDirectionMax() {
		return windDirectionMax;
	}

	public void setWindDirectionMax(int windDirectionMax) {
		this.windDirectionMax = windDirectionMax;
	}

	public boolean isHumidityRelevant() {
		return humidityRelevant;
	}

	public void setHumidityRelevant(boolean humidityRelevant) {
		this.humidityRelevant = humidityRelevant;
	}

	public int getHumidityMin() {
		return humidityMin;
	}

	public void setHumidityMin(int humidityMin) {
		this.humidityMin = humidityMin;
	}

	public int getHumidityMax() {
		return humidityMax;
	}

	public void setHumidityMax(int humidityMax) {
		this.humidityMax = humidityMax;
	}

	public boolean isChanceOfPrecipitationRelevant() {
		return chanceOfPrecipitationRelevant;
	}

	public void setChanceOfPrecipitationRelevant(
			boolean chanceOfPrecipitationRelevant) {
		this.chanceOfPrecipitationRelevant = chanceOfPrecipitationRelevant;
	}

	public int getChanceOfPrecipitationMin() {
		return chanceOfPrecipitationMin;
	}

	public void setChanceOfPrecipitationMin(int chanceOfPrecipitationMin) {
		this.chanceOfPrecipitationMin = chanceOfPrecipitationMin;
	}

	public int getChanceOfPrecipitationMax() {
		return chanceOfPrecipitationMax;
	}

	public void setChanceOfPrecipitationMax(int chanceOfPrecipitationMax) {
		this.chanceOfPrecipitationMax = chanceOfPrecipitationMax;
	}

	@Override
	public String toString() {
		return "AlarmConfig [on=" + on + "wakeTimeHours=" + wakeTimeHours
				+ ", wakeTimeMinutes=" + wakeTimeMinutes
				+ ", temperatureRelevant=" + temperatureRelevant
				+ ", temperatureMin=" + temperatureMin + ", temperatureMax="
				+ temperatureMax + ", pressureRelevant=" + pressureRelevant
				+ ", pressureMin=" + pressureMin + ", pressureMax="
				+ pressureMax + ", pressureChangeRelevant="
				+ pressureChangeRelevant + ", pressureChangeMin="
				+ pressureChangeMin + ", pressureChangeMax="
				+ pressureChangeMax + ", windSpeedRelevant="
				+ windSpeedRelevant + ", windSpeedMin=" + windSpeedMin
				+ ", windSpeedMax=" + windSpeedMax + ", windDirectionRelevant="
				+ windDirectionRelevant + ", windDirectionMin="
				+ windDirectionMin + ", windDirectionMax=" + windDirectionMax
				+ ", humidityRelevant=" + humidityRelevant + ", humidityMin="
				+ humidityMin + ", humidityMax=" + humidityMax
				+ ", chanceOfPrecipitationRelevant="
				+ chanceOfPrecipitationRelevant + ", chanceOfPrecipitationMin="
				+ chanceOfPrecipitationMin + ", chanceOfPrecipitationMax="
				+ chanceOfPrecipitationMax + "]";
	}
	
	
}

package com.jackbillings.alarm.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jackbillings.alarm.AlarmConfig;

/**
 * Servlet implementation class AlarmConfig
 */
public class AlarmConfigServlet extends HttpServlet {
	private static final String CONFIG_FILE_PATH = "/home/pi/alarm/alarm.properties";
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AlarmConfigServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		AlarmConfig config = new AlarmConfig(CONFIG_FILE_PATH); // read configuration from config file
		setRequestAttributesFromConfig(request, config); // store it for the JSP
		
		// forward request to JSP, which will display the config
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/config.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String hourString = request.getParameter("wakeTimeHours");
		String minuteString = request.getParameter("wakeTimeMinutes");
		String amPmString = request.getParameter("amPm");
		String minChanceOfPrecipString = request.getParameter("minChanceOfPrecipitation");
		String maxChanceOfPrecipString = request.getParameter("maxChanceOfPrecipitation");
		String minTempString = request.getParameter("minTemp");
		String maxTempString = request.getParameter("maxTemp");
		String minHumString = request.getParameter("minHum");
		String maxHumString = request.getParameter("maxHum");
		String minWindDirectionString = request.getParameter("minWindDirection");
		String maxWindDirectionString = request.getParameter("maxWindDirection");
		String minWindSpeedString = request.getParameter("minWindSpeed");
		String maxWindSpeedString = request.getParameter("maxWindSpeed");

		
		// Write user-specified parameter to config file
		AlarmConfig config = saveValues(hourString, minuteString, amPmString, minChanceOfPrecipString,
				maxChanceOfPrecipString, minTempString, maxTempString, minHumString, maxHumString,
				minWindDirectionString, maxWindDirectionString, minWindSpeedString, maxWindSpeedString);
		
		// Read the config file and stuff those values into the request object, which gets passed to the JSP page
		setRequestAttributesFromConfig(request, config);
		
		// Forward this request to the JSP page to display the form to the user
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/config.jsp");
		dispatcher.forward(request, response);
	}
	
	private void setRequestAttributesFromConfig(HttpServletRequest request, AlarmConfig config) {
		
		
		boolean pm = false;
		int wakeTimeHours = config.getWakeTimeHours();
		if (wakeTimeHours > 12) {
			wakeTimeHours -= 12;
			pm = true;
		}
		
		// TODO: Convert minutes int to zero-padded 2 digit string
		
		String amSelectedString = "";
		String pmSelectedString = "";
		if (pm) {
			pmSelectedString = "selected";
		} else {
			amSelectedString = "selected";
		}
		
		request.setAttribute("wakeTimeHours", Integer.toString(wakeTimeHours));
		request.setAttribute("wakeTimeMinutes", Integer.toString(config.getWakeTimeMinutes()));
		request.setAttribute("amSelectedString", amSelectedString);
		request.setAttribute("pmSelectedString", pmSelectedString);
		request.setAttribute("chanceOfPrecipitationMin", Integer.toString(config.getChanceOfPrecipitationMin()));
		request.setAttribute("chanceOfPrecipitationMax", Integer.toString(config.getChanceOfPrecipitationMax()));
		request.setAttribute("temperatureMin", Integer.toString(config.getTemperatureMin()));
		request.setAttribute("temperatureMax", Integer.toString(config.getTemperatureMax()));
		request.setAttribute("humidityMin", Integer.toString(config.getHumidityMin()));
		request.setAttribute("humidityMax", Integer.toString(config.getHumidityMax()));
		request.setAttribute("windDirectionMin", Integer.toString(config.getWindDirectionMin()));
		request.setAttribute("windDirectionMax", Integer.toString(config.getWindDirectionMax()));
		request.setAttribute("windSpeedMin", Integer.toString(config.getWindSpeedMin()));
		request.setAttribute("windSpeedMax", Integer.toString(config.getWindSpeedMax()));
	}
	
	private AlarmConfig saveValues(String hourString, String minuteString, String amPmString,
			String minChanceOfPrecipString, String maxChanceOfPrecipString, String minTempString, String maxTempString,
			String minHumString, String maxHumString, String minWindDirectionString, String maxWindDirectionString,
			String minWindSpeedString, String maxWindSpeedString) throws IOException {

		AlarmConfig config = new AlarmConfig(CONFIG_FILE_PATH);
		int hours = Integer.parseInt(hourString);
		if ((hours < 12) && ("pm".equalsIgnoreCase(amPmString))) {
			hours += 12; // convert to 24 hour clock time
		}
		if (hours > 23) {
			hours = 23;
		}
		if (hours < 0) {
			hours = 0;
		}
		config.setWakeTimeHours(hours);
		
		int minutes = Integer.parseInt(minuteString);
		if (minutes < 0) {
			minutes = 0;
		}
		if (minutes > 59) {
			minutes = 59;
		}
		config.setWakeTimeMinutes(minutes);
		
		int minChanceOfPrecip = Integer.parseInt(minChanceOfPrecipString);
		if (minChanceOfPrecip < 0) {
			minChanceOfPrecip = 0;
		}
		if (minChanceOfPrecip > 100) {
			minChanceOfPrecip = 100;
		}
		config.setChanceOfPrecipitationRelevant(true);
		config.setChanceOfPrecipitationMin(minChanceOfPrecip);
		
		int maxChanceOfPrecip = Integer.parseInt(maxChanceOfPrecipString);
		if (maxChanceOfPrecip < 0) {
			maxChanceOfPrecip = 0;
		}
		if (maxChanceOfPrecip > 100) {
			maxChanceOfPrecip = 100;
		}
		config.setChanceOfPrecipitationRelevant(true);
		config.setChanceOfPrecipitationMax(maxChanceOfPrecip);
		
		int minTemp = Integer.parseInt(minTempString);
		config.setTemperatureRelevant(true);
		config.setTemperatureMin(minTemp);
		
		int maxTemp = Integer.parseInt(maxTempString);
		config.setTemperatureRelevant(true);
		config.setTemperatureMax(maxTemp);
		
		int minHum = Integer.parseInt(minHumString);
		if (minHum < 0) {
			minHum = 0;
		}
		if (minHum > 100) {
			minHum = 100;
		}
		config.setHumidityRelevant(true);
		config.setHumidityMin(minHum);
		
		int maxHum = Integer.parseInt(maxHumString);
		if (maxHum < 0) {
			maxHum = 0;
		}
		if (maxHum > 100) {
			maxHum = 100;
		}
		config.setHumidityRelevant(true);
		config.setHumidityMax(maxHum);
		
		int minWindDirection = Integer.parseInt(minWindDirectionString);
		if (minWindDirection < 0) {
			minWindDirection = 0;
		}
		if (minWindDirection > 360) {		//should I even have these checks? can the computer convert 400 to 40 automatically?
			minWindDirection = 0;
		}
		config.setWindDirectionRelevant(true);
		config.setWindDirectionMin(minWindDirection);
		
		int maxWindDirection = Integer.parseInt(maxWindDirectionString);
		if (maxWindDirection < 0) {
			maxWindDirection = 0;
		}
		if (maxWindDirection > 360) {		//should I even have these checks? can the computer convert 400 to 40 automatically?
			maxWindDirection = 360;			//what if min > max?
		}
		config.setWindDirectionRelevant(true);
		config.setWindDirectionMax(maxWindDirection);
		
		int minWindSpeed = Integer.parseInt(minWindSpeedString);
		config.setWindSpeedRelevant(true);
		config.setWindSpeedMin(minWindSpeed);
		
		int maxWindSpeed = Integer.parseInt(maxWindSpeedString);
		config.setWindSpeedRelevant(true);
		config.setWindSpeedMax(maxWindSpeed);
				
		// disable all other parameters
		config.setHumidityRelevant(false);
		config.setPressureChangeRelevant(false);
		config.setPressureRelevant(false);
		config.setTemperatureRelevant(false);
		config.setWindDirectionRelevant(false);
		config.setWindSpeedRelevant(false);
		
		config.save(CONFIG_FILE_PATH);
		
		return config;
	}

}

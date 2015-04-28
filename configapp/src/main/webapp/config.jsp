<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alarm Configuration</title>
</head>
<body>
<h1>Smart Alarm Configuration</h1>
<form method="POST" action="AlarmConfig">

<p>Wake time: 
	<input type="text" name="wakeTimeHours" size="2" maxlength="2" 
		value="<%= (String)request.getAttribute("wakeTimeHours") %>">:
	<input type="text" name="wakeTimeMinutes" size="2" maxlength="2" 
		value="<%= (String)request.getAttribute("wakeTimeMinutes") %>">
	<select name="amPm" size="1">
		<option value="AM" <%= (String)request.getAttribute("amSelectedString") %>>AM</option>
		<option value="PM" <%= (String)request.getAttribute("pmSelectedString") %>>PM</option>
	</select>
	</p>

<h2>Wake Up Conditions</h2>

<p>Minimum chance of precipitation: <input type="text" name="minChanceOfPrecipitation" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("chanceOfPrecipitationMin") %>">%</p>
<p>Maximum chance of precipitation: <input type="text" name="maxChanceOfPrecipitation" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("chanceOfPrecipitationMax") %>">%</p>
<p>Minimum temperature: <input type="text" name="minTemp" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("temperatureMin") %>">&#176;</p>
<p>Maximum temperature: <input type="text" name="maxTemp" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("temperatureMax") %>">&#176;</p>
<p>Minimum humidity: <input type="text" name="minHum" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("humidityMin") %>">%</p>
<p>Maximum humidity: <input type="text" name="maxHum" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("humidityMax") %>">%</p>
<p>Minimum wind direction east of north: <input type="text" name="minWindDirection" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("windDirectionMin") %>">&#176;</p>
<p>Maximum wind direction east of north: <input type="text" name="maxWindDirection" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("windDirectionMax") %>">&#176;</p>
<p>Minimum wind speed: <input type="text" name="minWindSpeed" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("windSpeedMin") %>">mph</p>
<p>Maximum wind speed: <input type="text" name="maxWindSpeed" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("windSpeedMax") %>">mph</p>

<br>
<input type="SUBMIT" value="Save">

</form>

</body>
</html>
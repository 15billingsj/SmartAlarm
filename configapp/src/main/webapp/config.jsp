<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alarm Configuration</title>
</head>
<body>
<h1>Weather-Aware Alarm Configuration</h1>
<form method="POST" action="AlarmConfig">

<p>Wakeup Time</p>

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

<p>Wakeup Conditions</p>

<p>Maximum chance of precipitation: <input type="text" name="maxChanceOfPrecipitation" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("chanceOfPrecipitationMax") %>">%</p>
<p>Minimum chance of precipitation: <input type="text" name="minChanceOfPrecipitation" size="3" maxlength="3"
	value="<%= (String)request.getAttribute("chanceOfPrecipitationMin") %>">%</p>


<br>
<input type="SUBMIT" value="Save">

</form>

</body>
</html>
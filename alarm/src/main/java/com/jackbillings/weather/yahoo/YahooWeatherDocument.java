package com.jackbillings.weather.yahoo;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jackbillings.weather.PressureChange;
import com.jackbillings.weather.WeatherPojo;

public class YahooWeatherDocument {
	
	// temp, pressure, pressureChange, windSpeed, windDirection, humidity, chanceOfPrecipitation
	private int temperature;
	private float pressure;
	private PressureChange pressureChange;
	private int windSpeed;
	private int windDirection;
	private int humidity;
	private int chanceOfPrecipitation;
	
	public YahooWeatherDocument(Document doc) throws Exception {
		parse(doc);
	}

    private void parse(Document doc) throws Exception {
    	boolean found=false;
        try {
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("rss");

            for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
                Node node = nodes.item(nodeIndex);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    temperature = getIntValue("Temperature", node, elem, "yweather:condition", "temp");
                    humidity = getIntValue("Humidity", node, elem, "yweather:atmosphere", "humidity");
                    pressure = getFloatValue("Pressure", node, elem, "yweather:atmosphere", "pressure");
                    pressureChange = getPressureChange(node, elem);
                    windSpeed = getIntValue("Wind speed", node, elem, "yweather:wind", "speed");
                    windDirection = getIntValue("Wind direction", node, elem, "yweather:wind", "direction");
                    found = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!found) {
        	throw new Exception("Weather data not found in document");
        }
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
	
	private int getIntValue(String valueName, Node node, Element elem,
			String tag, String attribute) throws Exception {
    	int value=0;
    	boolean found=false;
    	
    	NodeList tagNodeList = elem.getElementsByTagName(tag);

        for (int i = 0; i < tagNodeList.getLength(); i++) {
            Node tagNode = tagNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) tagNode;
                String valueString = element.getAttribute(attribute);
                value = Integer.parseInt(valueString);
                found = true;
            }
        }
        if (!found) {
        	throw new Exception(valueName + " not found in document");
        }
        return value;
    }
	
	private float getFloatValue(String valueName, Node node, Element elem,
			String tag, String attribute) throws Exception {
    	float value=0.0f;
    	boolean found=false;
    	
    	NodeList tagNodeList = elem.getElementsByTagName(tag);

        for (int i = 0; i < tagNodeList.getLength(); i++) {
            Node tagNode = tagNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) tagNode;
                String valueString = element.getAttribute(attribute);
                value = Float.parseFloat(valueString);
                found = true;
            }
        }
        if (!found) {
        	throw new Exception(valueName + " not found in document");
        }
        return value;
    }
    
    private PressureChange getPressureChange(Node node, Element elem) throws Exception {
    	PressureChange pressureChange = PressureChange.STEADY;
    	boolean found = false;
    	
    	NodeList nodeList = elem.getElementsByTagName("yweather:atmosphere");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element atmosphereElement = (Element) subNode;
                String pressureChangeString = atmosphereElement.getAttribute("rising");
                if ("0".equals(pressureChangeString)) {
                	pressureChange = PressureChange.STEADY;
                } else if ("1".equals(pressureChangeString)) {
                	pressureChange = PressureChange.RISING;
                } else if ("2".equals(pressureChangeString)) {
                	pressureChange = PressureChange.FALLING;
                } else {
                	throw new Exception("Invalid pressure change value: " + pressureChangeString);
                }

                found = true;
            }
        }
        if (!found) {
        	throw new Exception("Pressure change value not found in document");
        }
        return pressureChange;
    }

}

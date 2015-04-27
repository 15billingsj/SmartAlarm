package com.jackbillings.weather.yahoo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.jackbillings.weather.City;
import com.jackbillings.weather.PressureChange;
import com.jackbillings.weather.Weather;
import com.jackbillings.weather.WeatherDao;
import com.jackbillings.weather.WeatherPojo;

public class YahooWeatherDao implements WeatherDao {
	final static Logger logger = Logger.getLogger(YahooWeatherDao.class);
	private City city = City.ACTON;

	public YahooWeatherDao() {
	}
	
	public void setCity(City city) {
		this.city = city;
	}

	public WeatherPojo getWeather() throws Exception {
		Document doc = getXmlFromYahoo(getYahooCityCode(city));
		// dumpXml(doc);
		YahooWeatherDocument weatherDoc = new YahooWeatherDocument(doc);

		WeatherPojo weather = new WeatherPojo(weatherDoc.getTemperature(),
				weatherDoc.getPressure(), weatherDoc.getPressureChange(),
				weatherDoc.getWindSpeed(), weatherDoc.getWindDirection(),
				weatherDoc.getHumidity(), getChanceOfPrecipitation(weatherDoc));

		return weather;
	}
	
	/**
	 * Make a crude approximate of chance of precip based on pressure / humidity
	 * @return
	 */
	private int getChanceOfPrecipitation(YahooWeatherDocument weatherDoc) {
		float pressure = weatherDoc.getPressure();
		
		if (weatherDoc.getHumidity() > 95) {
			return weatherDoc.getHumidity();
		}
		
		// adjust pressure up or down if it's rising or falling
		if (weatherDoc.getPressureChange() == PressureChange.RISING) {
			pressure += .2;
		}
		if (weatherDoc.getPressureChange() == PressureChange.FALLING) {
			pressure -= .2;
		}
		
		if (pressure < 29.75) {
			return 75;
		}
		
		if (pressure > 30.25) {
			return 25;
		}
		
		return 50;
	}
	
	/**
	 * Returns the yahoo location code for the given city
	 * See https://developer.yahoo.com/weather/ and
	 * Get code by searching zip code here: https://weather.yahoo.com/
	 * then looking in URL
	 * @param city the city
	 * @return the yahoo location code
	 */
	private String getYahooCityCode(City city) {
		switch (city) {
		case ACTON:
			return "12758549";
		case SARANAC_LAKE:
			return "2489017";
		case ST_LOUIS:
			return "12785615";
		default:
			throw new UnsupportedOperationException("City " + city.toString() + " not supported");
		}
	}
	
	private Document getXmlFromYahoo(String code) throws IOException {

        String url = null;

        // creating the URL
        url = "http://weather.yahooapis.com/forecastrss?w=" + code + "&u=f"; // f=Fahrenheit
        URL xmlUrl = new URL(url);
        InputStream in = xmlUrl.openStream();

        // parsing the XmlUrl
        Document doc = parse(in);

        return doc;
    }

    private Document parse(InputStream is) {
        Document doc = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;

        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();

            doc = builder.parse(is);
        } catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return doc;
    }
    
    private void dumpXml(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        logger.info("\n\nXML IN String format is:\n" + writer.toString());
	}
}

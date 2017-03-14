package com.ecs.weather.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ecs.weather.bean.Config;
import com.ecs.weather.resources.PDFConverter;
import com.ecs.weather.resources.ResourceHandler;
import com.ecs.weather.resources.RestUtils;

public class FetchWeather {

    private static final Logger logger = LogManager.getLogger(FetchWeather.class);
    private static String QUERY="?";
    private static String AND="&";
    private static String EQUALS="=";
	
    public static void main(String[] args) throws FileNotFoundException, IOException {

    	String city = System.getProperty("city", "");
        String appHome = System.getProperty("appHome", "");
        
		if (city.equals("") || appHome.equals("")  ) {
			throw new IllegalArgumentException("USAGE: java -Dcity=<City Name> -DappHome=<comlete path of the project> FetchWeather");
		}
		
    	logger.debug("Initializing configs from properties files");
        ResourceHandler.initialize();
        
        final Config config = Config.getInstance();
        
        logger.debug("Fetching weather report for city: {}", city);
        fetchWeatherReport(config);
        
        RestUtils.shutdown();
    }

	private static void fetchWeatherReport(Config config) throws IOException {
		
		File weatherHtmlFile = null;
		String weatherPDFFile = null;
		
		//url=api.openweathermap.org/data/2.5/weather?q=London&APPID=e5119dc079684b4dd8e52fdf57ad2279
		StringBuilder url = new StringBuilder();
		
		url.append(config.getServerName());
		url.append(config.getWeatherURI());
		url.append(QUERY);
		url.append(config.queryKeyMap.get("city"));
		url.append(EQUALS);
		url.append(config.getCity());
		url.append(AND);
		url.append(config.queryKeyMap.get("appId"));
		url.append(EQUALS);
		url.append(config.getAppId());
		url.append(AND);
		url.append(config.queryKeyMap.get("mode"));
		url.append(EQUALS);
		url.append(config.getMode());
		
		logger.debug("Final url: {}", url);
		
		URI uri;
		try {
			uri = new URI(url.toString());
			weatherHtmlFile = RestUtils.fireGetRequest(uri, config.getCity());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		if (weatherHtmlFile != null && weatherHtmlFile.exists()) {
			
			weatherPDFFile = PDFConverter.convertHTMLToPDF(weatherHtmlFile, config.getCity());
			if (weatherPDFFile != null && !weatherPDFFile.isEmpty()) {
				logger.info("PDF Weather Report: {}", weatherPDFFile);
			} else {
				logger.error("PDF Weather Report is either empty or not created properly: {}", weatherPDFFile);
			}
			 
		} else {
			
			logger.error("File doesnt exist!!, please check: {}", weatherHtmlFile);
		}
		
	}

}

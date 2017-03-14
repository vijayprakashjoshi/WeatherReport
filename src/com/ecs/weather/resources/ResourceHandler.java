package com.ecs.weather.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ecs.weather.bean.Config;


public class ResourceHandler {

    private static final Logger logger = LogManager.getLogger(ResourceHandler.class);
    private static final Config config = Config.getInstance();

    public static void initialize() throws FileNotFoundException, IOException {
        
    	loadBaseConfigs();
        RestUtils.init();
    }

    private static void loadBaseConfigs() throws FileNotFoundException, IOException {
        
        final File propertiesFile = new File(System.getProperty("appHome") + "/resources/config.properties");

        try (final InputStream inputStream = new FileInputStream(propertiesFile);) {

            final Properties properties = new Properties();
            properties.load(inputStream);

            config.setServerName(properties.getProperty("serverName",
                "api.openweathermap.org"));
            config.setAppId(properties.getProperty("appId",
                "766053c60333161799aed1a091ac39ee"));
            config.setCity(System.getProperty("city",properties.getProperty("city")));
            config.setWeatherURI(properties.getProperty("weatherURI",
                "data/2.5/weather"));
            config.setMode(properties.getProperty("mode",
                    "html"));
         
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
            	if (key.toString().endsWith("_Key")) {
            		config.queryKeyMap.put(key.toString().replace("_Key", ""), properties.getProperty(key.toString()));
            	}
            }
            logger.debug("query Key Map: {} !!",config.queryKeyMap);
            logger.debug("Initialized general properties !!");
            
        }
    }

}

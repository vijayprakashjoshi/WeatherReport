package com.ecs.weather.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ecs.weather.bean.Config;


public class RestUtils {

    private static final Logger logger = LogManager.getLogger(RestUtils.class);

    private static CloseableHttpClient httpClient;
	static CookieStore cookieStore;

    public static void init() {
        
    	final Config config = Config.getInstance();
        
        final ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                return TimeUnit.SECONDS.toMillis(config.getKeepAliveTime());
            }
        };

        final DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(
            config.getRequestRetry(), false);

    	httpClient = HttpClientBuilder.create()
    			.setRetryHandler(retryHandler)
	            .setMaxConnTotal(config.getMaxConnTotal())
	            .setKeepAliveStrategy(keepAliveStrategy)
	            .setMaxConnPerRoute(config.getMaxConnPerRoute())
	            .build();
    	
    	   logger.debug("Initialized httpClient properties !!");
    }

    public static File fireGetRequest(URI url, String city) {
    	
        HttpGet getMethod = null;
        HttpResponse httpResponse = null;
        File weatherHtmlFile = null;
        
        try {

        	getMethod = new HttpGet("http://" + url);
        	httpResponse = httpClient.execute(getMethod);
            
        	final int statusCode = httpResponse.getStatusLine().getStatusCode();
        	
        	if (statusCode == HttpStatus.SC_OK) {
        		logger.debug("Request fired successfully !!");
        	}
        	
        	HttpEntity entity = httpResponse.getEntity();
        	weatherHtmlFile = new File(System.getProperty("appHome")+"/output/HTMLReports/"+city +"_"+"Weather.html");

        	logger.debug("HTML File location: {}/output/HTMLReports/{}_Weather.html", System.getProperty("appHome"), city);
            
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                OutputStream outputStream = new FileOutputStream(weatherHtmlFile);
                IOUtils.copy(inputStream, outputStream);
                outputStream.close();
            }
            
        } catch (final Exception e) {
            logger.error(e.getMessage() + ": " + url, e);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
        }

        return weatherHtmlFile; 
        
    }

    public static void shutdown() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (final IOException e) {
                logger.error("Error shutting down HttpClient", e);
            }
            httpClient = null;
        }
        
    }
	
}

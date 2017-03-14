package com.ecs.weather.bean;

import java.util.HashMap;
import java.util.Map;

public class Config {

    // URLs and other specific details.
    private String serverName;
    private String appId;
    private String city;
    private String weatherURI;
    private String mode;
    
    // HttpClient
    private int maxConnTotal;
    private int maxConnPerRoute;
    private int keepAliveTime;
    private int requestRetry;

    
    public Map<String, String> queryKeyMap = new HashMap <String, String>();
    
    private final static Config config = new Config();

    public static Config getInstance() {
        return config;
    }
    
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeatherURI() {
		return weatherURI;
	}

	public void setWeatherURI(String weatherURI) {
		this.weatherURI = weatherURI;
	}    
    
	public int getMaxConnTotal() {
		return maxConnTotal;
	}

	public void setMaxConnTotal(int maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}

	public int getMaxConnPerRoute() {
		return maxConnPerRoute;
	}

	public void setMaxConnPerRoute(int maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getRequestRetry() {
		return requestRetry;
	}

	public void setRequestRetry(int requestRetry) {
		this.requestRetry = requestRetry;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
}

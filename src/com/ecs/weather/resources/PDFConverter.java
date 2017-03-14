package com.ecs.weather.resources;

import com.ecs.weather.report.FetchWeather;
import com.pdfcrowd.*;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PDFConverter {
	
	private static final Logger logger = LogManager.getLogger(PDFConverter.class);
	
	public static String convertHTMLToPDF(File weatherHtmlFile, String city) throws IOException {
		
		FileOutputStream fileStream = null ;    
		
		try 
        {
        
            // create an API client instance
            Client client = new Client("vijoshi03", "04358b4b69e8bcd314c576429971044b");

            // convert an HTML file
            fileStream = new FileOutputStream(System.getProperty("appHome")+"/output/PDFReports/"+city+"_WeatherReport.pdf");
            client.convertFile(weatherHtmlFile.getAbsolutePath(), fileStream);
            fileStream.close();

        }
        catch(PdfcrowdError why) {
        	logger.error(why.getMessage());
        }
        catch(IOException exc) {
        	
        	logger.error("IOException Occurred: {}",exc);

        	if (fileStream!=null){
            	fileStream.close();
            }
        }
		
		return (System.getProperty("appHome")+"/output/PDFReports/"+city+"_WeatherReport.pdf");
		
		
		
	}

}

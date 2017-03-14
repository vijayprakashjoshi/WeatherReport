API Key generated for access:

- In order to access the api's of above url, we would need api key to authenticate our requests.
	For the same, have created a registered to this site and created a temporary appId which i have used to meet the goal of this assignment.

- I am generating the final reports in HTML format, which i m later converting into pdf format using pdfcrowd.jar.
	For this as well, have created a temporary appId under site: https://pdfcrowd.com
	There are many other ways to achieve the above task (converting HTML to PDF) as well.

Usage:

1. Attached zip file contains the java classes along with the required libraries.
2. Unzip the file and go to unzipped directory and run following commands:
	sh run.sh <City Name>
3. This command will require a parameter as well which is the city name for which you need to fetch the weather report.

Details of Project:

a) Log Files:
	1. Have configured log4j as well for this project, which will be generated under the location: <HomeDirectory>/logs.
	2. Currently this log4j is configured for info level.

b) Properties File:
	1. All properties are configured under file: <HomeDirectory>/resources/config.properties
	2. This file contains the server name, weather URI, mode of output file, HttpClient properties and other properties which might change in future.


Sample Output:

	$ sh run.sh Bangalore
	Bangalore
	Report have been created at Location: /Users/vi365267/Documents/workspace/WeatherReport/output/PDFReports/Bangalore_WeatherReport.pdf
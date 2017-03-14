#!/bin/bash

if [ $# -lt 1 ] || [ $1 == "--usage" ] || [ $1 == "--help" ]
then
    echo "\nUsage: $0 <City Name >";
    echo "This is the city name for which weather report will be created \n\n";
    exit 1;
fi

city=$1
appHome=$(pwd)

java -cp $(echo lib/*.jar | tr ' ' ':'):bin -Dcity=${city} -DappHome=$(pwd) com.ecs.weather.report.FetchWeather

echo "Report have been created at Location: $(pwd)/output/PDFReports/${city}_WeatherReport.pdf"
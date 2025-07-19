# Horizon - WeatherApp
Horizon is a weather app which has the following features:

1)Displays current weather.  
2)Displays 5-day weather forecast.  
3)Shows air pollution.  
4)Displays high alerts in situations such as high temperature, thunderstorm, high AQI, etc.  

There are three layers to this application, each independent from the other:  

1)Interface layer which has two interchangeable interfaces: GUI and console-based.  
2)Business logic layer which is independent from both of the layers.  
3)Database layer which has two interchangeable databases: SQL and text-based. For SQL-based, we used MySQL and created schema and tables for it.  
 We created one JAR file which has all of the compiled classes in it.  
 To run the JAR file, enter the following commands in the Command Prompt:  
 cd *Enter path of the project folder*  
 java -cp "lib\json-20240303.jar;lib\mysql-connector-j-8.3.0.jar;.\WeatherApp.jar" Main  

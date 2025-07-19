drop database Horizon;
CREATE DATABASE IF NOT EXISTS Horizon;

USE Horizon;

CREATE TABLE IF NOT EXISTS CurrentWeather (
     Gen_Time VARCHAR(20) PRIMARY KEY,
     Gen_Date VARCHAR(20),
    Location VARCHAR(50),
	Temperature double,
    Visibility INT,
    Humidity double,
    FeelsLike double,
    WeatherDesc VARCHAR(50),
    RainVol1H double,
    RainVol3H double
);

CREATE TABLE IF NOT EXISTS FiveDayWeather (
    Gen_Time VARCHAR(20),
    Gen_Day INT,
    Gen_Date VARCHAR(20),
    Location VARCHAR(50),
	Temperature double,
    Visibility INT,
    Humidity double,
    FeelsLike double,
    WeatherDesc VARCHAR(50),
    RainVol1H double,
    RainVol3H double
);

CREATE TABLE IF NOT EXISTS AirPollution (
    Gen_Time VARCHAR(20) PRIMARY KEY,
    Gen_Date VARCHAR(20),
    Location VARCHAR(20),
    AQI double,
    CarbonMonoxide double,
    NitrogenMonoxide double,
    NitrogenDioxide double,
    Ozone double,
    SulfurDioxide double,
    Ammonia double,
    CoarsePartMatter double,
    FinePartMatter double
);

SELECT * FROM CurrentWeather;
SELECT * FROM AirPollution;
SELECT * FROM FiveDayWeather;
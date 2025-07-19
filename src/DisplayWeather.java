// ----------------------------------------Turns obj info into a string
public class DisplayWeather{

    //for double
    public String checkNA(double value)
    {
        if(value == -1)
        {
            return "N/A";
        }
        else
            return String.valueOf(value);
    }

    //for int
    public String checkNA(int value)
    {
        if(value == -1)
        {
            return "N/A";
        }
        else
            return String.valueOf(value);
    }

    //for string
    public String checkNA(String value)
    {
        if(value=="")
        {
            return "N/A";
        }
        else
            return String.valueOf(value);
    }
    
    
    public String displayWeather(WeatherInfo obj, String location, SunInfo obj2)
    {
        int visibility = obj.getVisibility();
        double humidity = obj.getHumidity();
        double feels_like = obj.getFeelsLike();
        String desc= obj.getWeatherDescr();
        double temperature = obj.getTemperature();
        double rain1 = obj.getRainVol1h();
        double rain3 = obj.getRainVol3h();
        String sunsetTime = obj2.getSunset_Time();
        String sunriseTime= obj2.getSunrise_Time();
        
        String DisplayInfoGUI = "Weather for " + location
                            + "\nVisibility: " + checkNA(visibility) + "m\n" 
                            + "Humidity: " + checkNA(humidity) + "%\n"
                            + "Feels like: " + checkNA(feels_like) + "°C\n"
                            + "Weather Description: " + checkNA(desc) + "\n"
                            + "Temperature: " + checkNA(temperature) + "°C\n"
                            + "Rain Volume (past 1h): " + checkNA(rain1) + "mm\n"
                            + "Rain Volume (past 3h): " + checkNA(rain3) + "mm\n"
                            + "Sunrise Time: " + checkNA(sunriseTime) + "\n"
                            + "Sunset Time: " + checkNA(sunsetTime) + "\n";


        return DisplayInfoGUI;
    }
       
}


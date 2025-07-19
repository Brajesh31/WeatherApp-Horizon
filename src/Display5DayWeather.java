import java.util.ArrayList;

// ----------------------------------------Turns obj info into a string
public class Display5DayWeather{

    //for double
    public String checkNA(Double value)
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
    
    public String display5DaysWeather(ArrayList<WeatherInfo> obj, String location)
    {
        
        String DisplayInfoGUI = "Five day weather: \n";
        int i = 1 ;

        for (WeatherInfo weather : obj) {
            int visibility = weather.getVisibility();
            double humidity = weather.getHumidity();
            double feels_like = weather.getFeelsLike();
            String desc= weather.getWeatherDescr();
            double temperature = weather.getTemperature();
            double rain1 = weather.getRainVol1h();
            double rain3 = weather.getRainVol3h();
            DisplayInfoGUI += "Day" + i + ":\n"+
                        "\nVisibility: " + checkNA(visibility) + "m\n" 
                        + "Humidity: " + checkNA(humidity) + "%\n" 
                        + "Feels like: " + checkNA(feels_like) + "°C\n"
                        + "Weather Description: " + checkNA(desc) + "\n"
                        + "Temperature: " + checkNA(temperature) + "°C\n"
                        + "Rain Volume (past 1h): " + checkNA(rain1) + "mm\n"
                        + "Rain Volume (past 3h): " + checkNA(rain3) + "mm\n\n";
            i++;
        }

        return DisplayInfoGUI;
    }

}
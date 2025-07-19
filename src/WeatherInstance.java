import java.util.ArrayList;

// ------------------------------------------ WEATHER INSTANCE
public class WeatherInstance
{
    private WeatherInfo weatherinfo;
    private SunInfo suninfo;
    private AirPollutionInfo airpolinfo;
    private ArrayList<WeatherInfo> fivedayinfo;
    private DateTime date;
    private Location location;

    public WeatherInstance()
    {

    }

    public WeatherInstance(DateTime date, Location location, WeatherInfo weatherinfo, SunInfo suninfo, 
    AirPollutionInfo airpolinfo, ArrayList<WeatherInfo> fivedayinfo)
    {
        this.date = date;
        this.location = location;
        this.weatherinfo = weatherinfo;
        this.suninfo = suninfo;
        this.airpolinfo = airpolinfo;
        this.fivedayinfo = fivedayinfo;
    }

    public DateTime getDate()
    {
        return date;
    }

    public Location getLocation()
    {
        return location;
    }

    public WeatherInfo getWeatherInfo()
    {
        return weatherinfo;
    }

    public SunInfo getSunInfo()
    {
        return suninfo;
    }

    public AirPollutionInfo getAirPollutionInfo()
    {
        return airpolinfo;
    }
    
    public ArrayList<WeatherInfo> getFiveDayWeather()
    {
        return fivedayinfo;
    }

    public void setDate(DateTime date)
    {
        this.date = date;
    }

    public void setLocation(Location loc)
    {
        this.location = loc;
    }
    
    public void setWeatherInfo(WeatherInfo weatherInfo)
    {
        this.weatherinfo = weatherInfo;
    }

    public void setAirPollutionInfo(AirPollutionInfo airpolinfo)
    {
        this.airpolinfo = airpolinfo;
    }

    public void setSunInfo(SunInfo sunInfo)
    {
        this.suninfo = sunInfo;
    }

    public void setFiveDayWeather(ArrayList<WeatherInfo> fiveDayWeather) 
    {
        this.fivedayinfo = fiveDayWeather;
    }

}

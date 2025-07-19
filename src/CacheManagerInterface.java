import java.util.ArrayList;

// ---------------------Interface for the 2 database managers

public interface CacheManagerInterface {
    public WeatherInfo checkTopValueWeather(String date, String location);
    public ArrayList<WeatherInfo> checkTopValueFiveDay(String date, String location);
    public AirPollutionInfo checkTopValueAirPol(String date, String location);
}

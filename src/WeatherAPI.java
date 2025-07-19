import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherAPI extends API
{
    private static final String API_KEY = "42c9e7c4ab03739b4d29f144eaef927b";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    
    @Override 
    public String APIcall(String location)
    {
        try {
            String apiUrl = String.format(API_URL, location, API_KEY);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                //---------------------------Info from API call
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\\n");
                }
                in.close();
                return response.toString();
            } 
            
            else 
            {
                return "Connection error. Response code: " + responseCode;
            }
        } 
        catch (Exception e) {
            return "Error: Unable to fetch weather data\nException: " + e.getMessage();
        }
    }

    public WeatherInfo getInfo(String location)
    {
        String weatherData = this.APIcall(location);

        JSONObject jsonObject = new JSONObject(weatherData);
        WeatherInfo obj = getFormData(jsonObject, weatherData);
        return obj;
    }

    //---------------------------- Make WeatherInfo Object
    public WeatherInfo getFormData(JSONObject jsonObject, String weatherData)
    {
        double temperature = tryExtractDouble(jsonObject,"main","temp");
        double feelsLike = tryExtractDouble(jsonObject,"main","feels_like");
        double humidity = tryExtractDouble(jsonObject,"main","humidity");

        //visibility exception handling
        int visibility;
        try{
            visibility = jsonObject.getInt("visibility");
        }
        catch (Exception e)
        {
            visibility = -1;
        }

        String description =tryExtractString(jsonObject, "description");

        double rainVol1h = 0.0;
        double rainVol3h = 0.0;
        if(weatherData.contains("\"rain\""))
        {
            if(weatherData.contains("\"rain\": {\"1h\":"))
            {
                rainVol1h = tryExtractDouble(jsonObject,"rain","1h");
            }
            if(weatherData.contains("\"rain\"") && weatherData.contains("\"3h\":"))
            {
                rainVol3h = tryExtractDouble(jsonObject,"rain","3h");
            }
        }

        WeatherInfo obj = new WeatherInfo(temperature, rainVol1h, rainVol3h, visibility, humidity, feelsLike, description);
        return obj;
    }

    public SunInfo getSunTimes(String location)
    {
        String weatherData = this.APIcall(location);

        JSONObject jsonObject = new JSONObject(weatherData);
        long sunriseUnix = jsonObject.getJSONObject("sys").getLong("sunrise");
        long sunsetUnix = jsonObject.getJSONObject("sys").getLong("sunset");

       SunInfo sun_info = new SunInfo(sunsetUnix, sunriseUnix);
       return sun_info; 
    }
    //----------------------------------exception handling for double value
    private double tryExtractDouble(JSONObject jsonObject, String key1, String key2)
    {
        double defaultvalue = -1.0;
        try {
            return jsonObject.getJSONObject(key1).getDouble(key2);
        } catch (Exception e) {
            return defaultvalue;
        }
        
    }
    private String tryExtractString(JSONObject jsonObject, String key) 
    {
        String defaultValue = "";
        try {
            return jsonObject.getJSONArray("weather").getJSONObject(0).getString(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}


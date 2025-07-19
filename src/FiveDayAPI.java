import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

public class FiveDayAPI extends API
{
    private static final String API_KEY = "9f0a94c33290f74d0a36ae700a156ab2";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s&units=metric";
    protected double Latitude;
    protected double Longitude;

    @Override 
    public String APIcall(String location)
    {
        try {
            String geocode = this.APIGeoCode(location);
            this.getLatLong(geocode);

            String apiUrl = String.format(API_URL, Latitude, Longitude, API_KEY);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                //------------------------Info from API call
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
            return "Error: Unable to fetch five-day weather data. Exception: " + e.getMessage();
        }
    }

    public String APIGeoCode(String location)
    {
        String API_Geo = "http://api.openweathermap.org/geo/1.0/direct?q=" + location + "&appid=" + API_KEY;
        try {
            URL url = new URL(API_Geo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                //------------------------Info from API call
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
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
            return "Error: Unable to fetch location. Exception: " + e.getMessage();
        }
    }

    //---------------------------- Make ArrayList for FiveDayWeather
    private ArrayList<WeatherInfo> parseWeatherData(String weatherData) {
    
        JSONObject jsonObject = new JSONObject(weatherData);
        JSONArray forecastList = jsonObject.getJSONArray("list");
    
        ArrayList<WeatherInfo> fivedayweather = new ArrayList<>(5);

        // ----------------------- Make ArrayList
        for (int i = 0; i < forecastList.length(); i++) {
            JSONObject forecastItem = forecastList.getJSONObject(i);
            String dateTime = forecastItem.getString("dt_txt");
    
            if (dateTime.endsWith("12:00:00")) {

                double temperature = tryExtractDouble(forecastItem,"main","temp");
                double feelsLike = tryExtractDouble(forecastItem,"main","feels_like");
                double humidity = tryExtractDouble(forecastItem,"main","humidity");

                //visibility exception handling
                int visibility;
                try{
                    visibility = forecastItem.getInt("visibility");
                }
                catch (Exception e)
                {
                    visibility = -1;
                }

                String description =tryExtractString(forecastItem, "description");

                double rainVol1h = 0.0;
                double rainVol3h = 0.0;

                if(weatherData.contains("\"rain\""))
                {
                    if(weatherData.contains("\"rain\": {\"1h\":"))
                    {
                        rainVol1h = tryExtractDouble(forecastItem,"rain","1h");
                    }
                    if(weatherData.contains("\"rain\"") && weatherData.contains("\"3h\":"))
                    {
                        rainVol3h = tryExtractDouble(forecastItem,"rain","3h");
                    }
                }

                WeatherInfo obj = new WeatherInfo(temperature, rainVol1h, rainVol3h, visibility, humidity, feelsLike, description);
    
                fivedayweather.add(obj);
            }
        }
        return fivedayweather;
    }
    
    public  ArrayList<WeatherInfo> getInfo(String location) 
    {
        String weatherData = this.APIcall(location);
        return parseWeatherData(weatherData);
    }
    
    // -------------------- Setting Latitude and Longitude
    public void setLatitude(double lati)
    {
        Latitude = lati;
    }
  
    public void setLongitude(double longi)
    {
        Longitude = longi;
    }

    public void getLatLong(String geoJSON) {
        double lat = 0, longi = 0;
        JSONArray jsonArray = new JSONArray(geoJSON);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            lat = jsonObject.getDouble("lat");
            longi = jsonObject.getDouble("lon");
        }

        this.setLatitude(lat);
        this.setLongitude(longi);
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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

//------------------------------------------------Text File Database
public class DB2WeatherInstance implements DBManagerInterface, CacheManagerInterface {
    
    public void saveInfo(WeatherInstance obj) {
        saveCurrentWeather(obj);
        saveFiveDayWeather(obj);
        saveAirPollution(obj);
    }

    //-----------------------------------------------Stores Current Weather in file
    private void saveCurrentWeather(WeatherInstance obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CurrentWeather.txt", true))) {
            String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    obj.getDate().getDate(), obj.getDate().getTime(), obj.getLocation().getCity(), obj.getWeatherInfo().getTemperature(),
                    obj.getWeatherInfo().getVisibility(), obj.getWeatherInfo().getHumidity() ,
                    obj.getWeatherInfo().getFeelsLike(), obj.getWeatherInfo().getWeatherDescr(), obj.getWeatherInfo().getRainVol1h(),
                    obj.getWeatherInfo().getRainVol3h(), obj.getSunInfo().getSunrise_Time(), obj.getSunInfo().getSunset_Time());
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //------------------------------------------------Stores 5 day weather in file
    private void saveFiveDayWeather(WeatherInstance obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FiveDayWeather.txt", true))) {
            ArrayList<WeatherInfo> fiveDayInfo = obj.getFiveDayWeather();
            int i = 1;
            for (WeatherInfo info : fiveDayInfo) {
                String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        obj.getDate().getDate(), obj.getDate().getTime(), obj.getLocation().getCity(), i++, info.getTemperature(), info.getVisibility(),
                        info.getHumidity(), info.getFeelsLike(), info.getWeatherDescr(), info.getRainVol1h(), info.getRainVol3h(), 
                        obj.getSunInfo().getSunrise_Time(), obj.getSunInfo().getSunset_Time());
                writer.write(data);
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }
    //-----------------------------------------------Stores Air Pollution in file
    private void saveAirPollution(WeatherInstance obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AirPollution.txt", true))) {
            AirPollutionInfo airPollutionInfo = obj.getAirPollutionInfo();
            String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    obj.getDate().getDate(), obj.getDate().getTime(), obj.getLocation().getCity(), airPollutionInfo.getAirQualityIndex(),
                    airPollutionInfo.getCO(), airPollutionInfo.getNO(),
                    airPollutionInfo.getNO2(), airPollutionInfo.getOzone(),
                    airPollutionInfo.getSO2(), airPollutionInfo.getAmmonia(),
                    airPollutionInfo.getpm10(), airPollutionInfo.getpm2_5());
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //-----------------------------------------------Cache Management from file
    //-----------------------------------------Gets Data from currentWeather.txt
    public WeatherInfo checkTopValueWeather(String date, String location) {
        WeatherInfo result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("CurrentWeather.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String lineDate = parts[0];
                String lineLocation = parts[2]; 

                if (lineDate.equals(date) && lineLocation.equals(location)) {
                    double temperature = Double.parseDouble(parts[3]);
                    int visibility = Integer.parseInt(parts[4]);
                    double humidity = Double.parseDouble(parts[5]);
                    double feelsLike = Double.parseDouble(parts[6]);
                    String weatherDescr = parts[7];
                    double rainVol1h = Double.parseDouble(parts[8]);
                    double rainVol3h = Double.parseDouble(parts[9]);
                    result = new WeatherInfo(temperature, rainVol1h, rainVol3h, visibility, humidity, feelsLike, weatherDescr);
                    System.out.println(parts[2]);
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------Gets Data from FiveDayWeather.txt
    public ArrayList<WeatherInfo> checkTopValueFiveDay(String date, String location) {
        ArrayList<WeatherInfo> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("FiveDayWeather.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); 
                String lineDate = parts[0]; 
                String lineLocation = parts[2]; 
                if (lineDate.equals(date) && lineLocation.equals(location)) {
                    double temperature = Double.parseDouble(parts[4]);
                    int visibility = Integer.parseInt(parts[5]);
                    double humidity = Double.parseDouble(parts[6]);
                    double feelsLike = Double.parseDouble(parts[7]);
                    String weatherDescr = parts[8];
                    double rainVol1h = Double.parseDouble(parts[9]);
                    double rainVol3h = Double.parseDouble(parts[10]);
                    WeatherInfo weatherInfo = new WeatherInfo(temperature, rainVol1h, rainVol3h, visibility, humidity, feelsLike, weatherDescr);
                    result.add(weatherInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    //-----------------------------------------Gets Data from AirPollution.txt
    public AirPollutionInfo checkTopValueAirPol(String date, String location) {
        AirPollutionInfo result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("AirPollution.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); 
                String lineDate = parts[0]; 
                String lineLocation = parts[2];

                if (lineDate.equals(date) && lineLocation.equals(location)) {
                    double airQualityIndex = Double.parseDouble(parts[3]);
                    double co = Double.parseDouble(parts[4]);
                    double no = Double.parseDouble(parts[5]);
                    double no2 = Double.parseDouble(parts[6]);
                    double ozone = Double.parseDouble(parts[7]);
                    double so2 = Double.parseDouble(parts[8]);
                    double ammonia = Double.parseDouble(parts[9]);
                    double pm10 = Double.parseDouble(parts[10]);
                    double pm2_5 = Double.parseDouble(parts[11]);
                    result = new AirPollutionInfo(airQualityIndex, co, no, no2, ozone, so2, ammonia, pm2_5, pm10);
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
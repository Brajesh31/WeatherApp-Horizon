//---------------------------------------------------- Weather
public class WeatherInfo
{
    private double Temperature;
    private double rainVol1h;
    private double rainVol3h;
    private int Visibility;
    private double Humidity;
    private double feelsLike;
    private String weatherDescr;

    public WeatherInfo(double temp, double rain1, double rain3, int visib, double humid,
    double feels, String descr)
    {
        Temperature = temp;
        rainVol1h = rain1;
        rainVol3h = rain3;
        Visibility = visib;
        Humidity = humid;
        feelsLike = feels;
        weatherDescr = descr;
    }

     // Getters for WeatherInfo properties
     public double getTemperature() {
        return Temperature;
    }

    public double getRainVol1h() {
        return rainVol1h;
    }

    public double getRainVol3h() {
        return rainVol3h;
    }

    public int getVisibility() {
        return Visibility;
    }

    public double getHumidity() {
        return Humidity;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public String getWeatherDescr() {
        return weatherDescr;
    }
}

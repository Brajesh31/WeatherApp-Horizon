//---------------------------------------------------Sunset / Sunrise timings
public class SunInfo
{
    private String sunrise_time;
    private String sunset_time;

    public SunInfo(long sunset_unix, long sunrise_unix)
    {
        sunrise_time = this.convertUnixTime(sunrise_unix);
        sunset_time = this.convertUnixTime(sunset_unix);
    }

    // Getters for SunInfo properties
    public String getSunrise_Time()
    {
        try{
            return sunrise_time;
        }
        catch(Exception e){
            return "";
        }
    }

    public String getSunset_Time()
    {
        try{
            return sunset_time;
        }
        catch(Exception e){
            return "";
        }
    }

   private String convertUnixTime(long unixTime) 
   {
        try {
            java.util.Date time = new java.util.Date(unixTime * 1000);
            return new java.text.SimpleDateFormat("HH:mm:ss").format(time);
        } catch (Exception e) {
            return "";
        }
    }
}

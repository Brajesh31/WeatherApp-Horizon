// ------------------------------------------ LOCATION
public class Location
{
    double Latitude;
    double Longitude;
    String City;

    public Location(double lat, double lon, String City)
    {
        Latitude = lat;
        Longitude = lon;
        this.City = City;
    }

    public double getLat()
    {
        return Latitude;
    }

    public double getLon()
    {
        return Longitude;
    }

    public String getCity()
    {
        return City;
    }
}

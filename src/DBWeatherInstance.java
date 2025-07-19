import java.sql.*;
import java.util.ArrayList;

//--------------------------------MYSQL Database

public class DBWeatherInstance implements DBManagerInterface, CacheManagerInterface {
    
    private Connection con;
    String url;
    String username;
    String password;

    public DBWeatherInstance() throws SQLException {
        this.url = "jdbc:mysql://localhost:3306/Horizon";
        this.username = "root";
        this.password = "12345678";
        this.con = DriverManager.getConnection(url, username, password);
    }

    public void closeConnection() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public void saveInfo(WeatherInstance obj)
    {
        WeatherInfo weatherinfo = obj.getWeatherInfo();
        AirPollutionInfo airpolinfo = obj.getAirPollutionInfo();
        ArrayList<WeatherInfo> fivedayweather = obj.getFiveDayWeather();
        Location location = obj.getLocation();

        DateTime datetime = obj.getDate();

        // -------------------------------- SAVE IN CurrentWeather
        String SQLCurrentWeather = "INSERT INTO CurrentWeather (Gen_Time, Gen_Date, Location, Temperature, Visibility, Humidity, FeelsLike, WeatherDesc, RainVol1H, RainVol3H)"+
        "VALUES ('" + datetime.getTime() + "', '"+datetime.getDate() +"', '" + location.getCity() + "', " + weatherinfo.getTemperature() + ", " + 
        weatherinfo.getVisibility() + ", " + weatherinfo.getHumidity() + ", " + weatherinfo.getFeelsLike() + ", '" + 
        weatherinfo.getWeatherDescr() + "', " + weatherinfo.getRainVol1h() + ", " + weatherinfo.getRainVol3h() + ")";
        
        try{
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(SQLCurrentWeather);
            System.out.println(rowsAffected + " row(s) affected.");
        }
        catch(SQLException e)
        {
            System.out.println("Could not insert Weather");
        }

        // -------------------------------- SAVE IN FiveDayWeather
        for(int i=0;i<5;i++)
        {
            WeatherInfo day_i = fivedayweather.get(i);
            String SQLFiveDay = "INSERT INTO FiveDayWeather (Gen_Time, Gen_Day, Gen_Date, Temperature, Location, Visibility, Humidity, FeelsLike, WeatherDesc, RainVol1H, RainVol3H) " +
                                "VALUES ('"+ datetime.getTime() +"', "+ i+1 +", '"+ datetime.getDate() +"', "+ day_i.getTemperature() +", '"+ location.getCity() +"', "+day_i.getVisibility()+", "+day_i.getHumidity()+", "+day_i.getFeelsLike()+", '"+day_i.getWeatherDescr()+"', "+day_i.getRainVol1h()+", "+day_i.getRainVol3h()+");";
            
            try{
                Statement stmt = con.createStatement();
                int rowsAffected = stmt.executeUpdate(SQLFiveDay);
                System.out.println(rowsAffected + " row(s) affected.");
            }
            catch(SQLException e)
            {
                System.out.println("Could not insert Five Day weather for Day " + i);
                e.getMessage();
            }
        }
       
        // -------------------------------- SAVE IN AirPollution
        String SQLAirPollution="INSERT INTO AirPollution (Gen_Time, Gen_Date, Location, AQI, CarbonMonoxide, NitrogenMonoxide, NitrogenDioxide, Ozone, SulfurDioxide, Ammonia, CoarsePartMatter, FinePartMatter)"+
        "VALUES ('"+ datetime.getTime() +"','"+ datetime.getDate() +"','"+ location.getCity() +"' ,"+ airpolinfo.getAirQualityIndex() +" ,"+ airpolinfo.getCO()+" ,"+ airpolinfo.getNO()+ " ,"+airpolinfo.getNO2() + " ,"+ airpolinfo.getOzone()+ " ,"+ airpolinfo.getSO2()+ " ,"+ airpolinfo.getAmmonia()+" , "+ airpolinfo.getpm2_5()+" ,"+ airpolinfo.getpm10()+ ")";

        try{
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(SQLAirPollution);
            System.out.println(rowsAffected + " row(s) affected.");
        }
        catch(SQLException e)
        {
            System.out.println("Could not insert Air Pollution");
        }
         
    }

    // -----------------------------CACHE FUNCTIONS

    public WeatherInfo checkTopValueWeather(String date, String location)
    {
        String latestDateSQL = "SELECT * " + 
                                "FROM CurrentWeather " + 
                                "WHERE Gen_Date='"+ date +"'" +
                                " AND Location = '"+ location +"'";
                
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(latestDateSQL)) {
           
            if (rs.next()) {

                double temp = rs.getDouble("Temperature");
                int visibility = rs.getInt("Visibility");
                double humidity = rs.getDouble("Humidity");
                double feelslike = rs.getDouble("FeelsLike");
                String WeatherDescr = rs.getString("WeatherDesc");
                double rainVol1h = rs.getDouble("RainVol1H");
                double rainVol3h = rs.getDouble("RainVol3H");
    
                WeatherInfo obj = new WeatherInfo(temp, rainVol1h, rainVol3h, visibility, humidity, feelslike, WeatherDescr);
                return obj;
            } else {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }

    public ArrayList<WeatherInfo> checkTopValueFiveDay(String date, String location)
    {
        ArrayList<WeatherInfo> fivedayweather = null;
        String latestDateSQL = "SELECT * " + 
                                "FROM FiveDayWeather " + 
                                "WHERE Gen_Date = '"+ date +"'" +
                                " AND Location = '"+ location +"'";
                
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(latestDateSQL)) {

            String fivedaysSQL = "SELECT * " + 
                            "FROM FiveDayWeather " + 
                            "WHERE Gen_Date = '"+ date +"'" +
                            " AND Location = '"+ location +"' ORDER BY Gen_Day ASC";

                            Statement stmt2 = con.createStatement();
                            ResultSet rs2 = stmt2.executeQuery(fivedaysSQL);
            fivedayweather = new ArrayList<>();
            
            while(rs2.next()) {

                double temp = rs2.getDouble("Temperature");
                int visibility = rs2.getInt("Visibility");
                double humidity = rs2.getDouble("Humidity");
                double feelslike = rs2.getDouble("FeelsLike");
                String WeatherDescr = rs2.getString("WeatherDesc");
                double rainVol1h = rs2.getDouble("RainVol1H");
                double rainVol3h = rs2.getDouble("RainVol3H");

                WeatherInfo obj = new WeatherInfo(temp, rainVol1h, rainVol3h, visibility, humidity, feelslike, WeatherDescr);
                fivedayweather.add(obj);
            }

            return fivedayweather;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fivedayweather;
    }

    public AirPollutionInfo checkTopValueAirPol(String date, String location)
    {
        String latestDateSQL = "SELECT * " + 
                                "FROM AirPollution " + 
                                "WHERE Gen_Date = '"+ date +"'" +
                                "AND Location = '"+ location +"'";
                
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(latestDateSQL)) {
           
            if (rs.next()) {

                double AQI = rs.getDouble("AQI");
                double co = rs.getDouble("CarbonMonoxide");
                double no = rs.getDouble("NitrogenMonoxide");
                double no2 = rs.getDouble("NitrogenDioxide");
                double ozone = rs.getDouble("Ozone");
                double so2 = rs.getDouble("SulfurDioxide");
                double ammonia = rs.getDouble("Ammonia");
                double pm10 = rs.getDouble("CoarsePartMatter");
                double pm2_5 = rs.getDouble("FinePartMatter");
    
                AirPollutionInfo obj = new AirPollutionInfo(AQI, co, no, no2, ozone, so2, ammonia, pm2_5, pm10);
                return obj;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

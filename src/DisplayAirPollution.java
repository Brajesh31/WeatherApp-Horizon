import javax.swing.*;

public class DisplayAirPollution extends JFrame{
    
    public String checkNA(double value)
    {
        if(value == -1)
        {
            return "N/A";
        }
        else
            return String.valueOf(value);
    }

    public String displayAirPol(AirPollutionInfo obj, String location)
    {
        double Aqi = obj.getAirQualityIndex();
        double co = obj.getCO();
        double no =  obj.getNO();
        double no2 = obj.getNO2();
        double oz = obj.getOzone();
        double so2 = obj.getSO2();
        double ammonia = obj.getAmmonia();
        double pm2_5 = obj.getpm2_5();
        double pm10 = obj.getpm10();

        String DisplayInfoGUI = "Air Pollution data for " + location + "\n"
                                + "AQI: " + checkNA(Aqi) 
                                + "\nCarbon Monoxide: " + checkNA(co) + " μg/m3"
                                + "\nNitrogen Monoxide: " + checkNA(no) + " μg/m3"
                                + "\nNitrogen Dioxide: " + checkNA(no2) + " μg/m3"
                                + "\nOzone: " + checkNA(oz) + " μg/m3"
                                + "\nSulfur Dioxide: " + checkNA(so2) + " μg/m3"
                                + "\nAmmonia: " + checkNA(ammonia) + " μg/m3"
                                + "\nCoarse particulate matter: " + checkNA(pm10) + " μg/m3"
                                + "\nFine particles matter: " + checkNA(pm2_5) + " μg/m3\n";

        return DisplayInfoGUI;
    }

}

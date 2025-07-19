//------------------------------------------------------ Air Pollution
public class AirPollutionInfo{
    private double AirQualityIndex;
    private double CO;
    private double NO;
    private double NO2;
    private double Ozone;
    private double SO2;
    private double Ammonia;
    private double pm2_5;
    private double pm10;

    public AirPollutionInfo(double AirQualityIndex, double CO, double NO, double N20, double Ozone, double SO2, double Ammonia, double pm2_5, double pm10) {
        this.AirQualityIndex = AirQualityIndex;
        this.CO = CO;
        this.NO = NO;
        this.NO2 = N20;
        this.Ozone = Ozone;
        this.SO2 = SO2;
        this.Ammonia = Ammonia;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
    }

    // Getters for AirpollutionInfo properties
    public double getAmmonia() {
        return Ammonia;
    }

    public double getOzone() {
        return Ozone;
    }

    public double getNO2() {
        return NO2;
    }

    public double getNO() {
        return NO;
    }

    public double getCO() {
        return CO;
    }

    public double getAirQualityIndex() {
        return AirQualityIndex;
    }

    public double getpm10() {
        return pm10;
    }

    public double getpm2_5() {
        return pm2_5;
    }

    public double getSO2(){
        return SO2;
    }
}


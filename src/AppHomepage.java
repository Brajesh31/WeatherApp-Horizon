import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AppHomepage extends JFrame{
    private JTextField locationField;
    private JTextArea currentWeatherOutputArea;
    private JTextArea fiveDayWeatherOutputArea;
    private JTextArea airPollutionOutputArea;
    private JButton getWeatherButton;
    private JTabbedPane tabbedPane;

    public AppHomepage(DBManagerInterface obj) {
        setTitle("Hor⚡zon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        // ------------------------Set program icon
        ImageIcon programIcon = new ImageIcon("4052984.png");
        setIconImage(programIcon.getImage());
    
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBorder(new EmptyBorder(70, 70, 70, 70)); // Increased border size
        JPanel currentWeatherPanel = new JPanel(new BorderLayout());
        JPanel fiveDayWeatherPanel = new JPanel(new BorderLayout());
        JPanel airPollutionPanel = new JPanel(new BorderLayout());
    
        currentWeatherPanel.setBorder(new TitledBorder(new LineBorder(new Color(120,120,200), 5), "Current Weather", TitledBorder.LEFT, TitledBorder.TOP)); // Increased border width
        fiveDayWeatherPanel.setBorder(new TitledBorder(new LineBorder(new Color(100,100,180), 5), "5-Day Weather Forecast", TitledBorder.LEFT, TitledBorder.TOP)); // Increased border width
        airPollutionPanel.setBorder(new TitledBorder(new LineBorder(new Color(80,80,160), 5), "Air Quality Index", TitledBorder.LEFT, TitledBorder.TOP)); // Increased border width
    
        tabbedPane.addTab("Current Weather", currentWeatherPanel);
        tabbedPane.addTab("5-Day Forecast", fiveDayWeatherPanel);
        tabbedPane.addTab("Air Quality", airPollutionPanel);
    
        ImageIcon backgroundIcon = new ImageIcon("background.jpeg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(tabbedPane, BorderLayout.CENTER);
    
        // Add backgroundLabel to the frame
        add(backgroundLabel, BorderLayout.CENTER);
    
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(40,46,120));
    
        // ----------------------------------Add logo
        ImageIcon logoIcon = new ImageIcon("4052984.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(64, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        inputPanel.add(logoLabel);
    
        JLabel locationLabel = new JLabel("Enter Location:");
        locationLabel.setFont(new Font("Century Gothic", Font.BOLD, 21));
        locationLabel.setForeground(new Color(200, 233, 225));
        inputPanel.add(locationLabel);
    
        locationField = new JTextField(20);
        locationField.setFont(new Font("Century Gothic", Font.BOLD, 16));
        inputPanel.add(locationField);
    
        getWeatherButton = new JButton("Show Weather");
        getWeatherButton.setFont(new Font("Century Goth", Font.BOLD, 16));
        getWeatherButton.setBackground(new Color(58, 133, 225));
        getWeatherButton.setForeground(Color.WHITE);
        inputPanel.add(getWeatherButton);
    
        add(inputPanel, BorderLayout.NORTH);
        
            displayLocationForm(obj);

    }
    
    public void displayLocationForm(DBManagerInterface obj) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(40, 46, 120));

        // ----------------------------------Add logo
        ImageIcon logoIcon = new ImageIcon("4052984.png"); 
        Image scaledImage = logoIcon.getImage().getScaledInstance(64, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        panel.add(logoLabel);
        

        JLabel locationLabel = new JLabel("Enter Location:");
        locationLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        locationLabel.setForeground(new Color(200, 233, 225));
        panel.add(locationLabel);

        locationField = new JTextField(20);
        locationField.setFont(new Font("Century Gothic", Font.BOLD, 16));
        panel.add(locationField);

        getWeatherButton = new JButton("Show Weather");
        getWeatherButton.setFont(new Font("Century Goth", Font.BOLD, 16));
        getWeatherButton.setBackground(new Color(34, 40, 49));
        getWeatherButton.setForeground(Color.WHITE);
        panel.add(getWeatherButton);

        add(panel, BorderLayout.NORTH);

        getWeatherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            try {
            
                // -------------------------------------------INIT DB 
                DBManagerInterface handler = obj;
                CacheManagerInterface cachehandler = new DBWeatherInstance();
                
                if (obj instanceof CacheManagerInterface) {
                    cachehandler = (CacheManagerInterface) obj;
                }

                // ------------------------getting location from the form
                String location = locationField.getText();
                
                DateTime date = new DateTime();
                Location locationObj = new Location(1.0, 1.0, location);
                WeatherInstance weather_inst = new WeatherInstance();

                WeatherInfo weather = cachehandler.checkTopValueWeather(date.getDate(), location);
                AirPollutionInfo airpol = cachehandler.checkTopValueAirPol(date.getDate(), location);
                ArrayList<WeatherInfo> fivedayweather = cachehandler.checkTopValueFiveDay(date.getDate(), location);

                WeatherAPI weather_api = new WeatherAPI();
                AirPollutionAPI airPollution_api = new AirPollutionAPI();
                FiveDayAPI fiveday_api = new FiveDayAPI();
                SunInfo suninfo = weather_api.getSunTimes(location);

                // API CALL if the object returned from cache managing functions is NULL 
                if(weather == null)
                {
                    weather = weather_api.getInfo(location);
                    airpol = airPollution_api.getInfo(location);
                    fivedayweather = fiveday_api.getInfo(location);
                    weather_inst = new WeatherInstance(date, locationObj, weather, suninfo, airpol, fivedayweather);

                    handler.saveInfo(weather_inst);
                }
                else{
                    weather_inst = new WeatherInstance(date, locationObj, weather, suninfo, airpol, fivedayweather);
                }
                
                displayInfo(weather_inst, weather, location);
            }
            catch (Exception er) {
                System.out.println("Could not connect");
                er.printStackTrace();
            }

            }
        });

        setVisible(true);
    }

    public void displayInfo(WeatherInstance weather_inst, WeatherInfo weather, String location) {
        DisplayWeather display_weather = new DisplayWeather();
        DisplayAirPollution display_airpol = new DisplayAirPollution();
        Display5DayWeather display_5Day = new Display5DayWeather();
        
        String currentWeatherOutput = display_weather.displayWeather(weather_inst.getWeatherInfo(), location,weather_inst.getSunInfo()) + "\n";
        String fiveDayWeatherOutput = display_5Day.display5DaysWeather(weather_inst.getFiveDayWeather(), location) + "\n";
        String airPollutionOutput = display_airpol.displayAirPol(weather_inst.getAirPollutionInfo(), location) + "\n";
        currentWeatherOutputArea = new JTextArea(currentWeatherOutput);
        fiveDayWeatherOutputArea = new JTextArea(fiveDayWeatherOutput);
        airPollutionOutputArea = new JTextArea(airPollutionOutput);

        Font font = new Font("Century Gothic", Font.PLAIN, 16);
        currentWeatherOutputArea.setFont(font);
        fiveDayWeatherOutputArea.setFont(font);
        airPollutionOutputArea.setFont(font);

        currentWeatherOutputArea.setLineWrap(true);
        currentWeatherOutputArea.setWrapStyleWord(true);
        fiveDayWeatherOutputArea.setLineWrap(true);
        fiveDayWeatherOutputArea.setWrapStyleWord(true);
        airPollutionOutputArea.setLineWrap(true);
        airPollutionOutputArea.setWrapStyleWord(true);

        currentWeatherOutputArea.setEditable(false);
        fiveDayWeatherOutputArea.setEditable(false);
        airPollutionOutputArea.setEditable(false);

        JScrollPane currentWeatherScrollPane = new JScrollPane(currentWeatherOutputArea);
        JScrollPane fiveDayWeatherScrollPane = new JScrollPane(fiveDayWeatherOutputArea);
        JScrollPane airPollutionScrollPane = new JScrollPane(airPollutionOutputArea);

        JPanel currentWeatherPanel = (JPanel) tabbedPane.getComponentAt(0);
        JPanel fiveDayWeatherPanel = (JPanel) tabbedPane.getComponentAt(1);
        JPanel airPollutionPanel = (JPanel) tabbedPane.getComponentAt(2);

        currentWeatherPanel.removeAll();
        currentWeatherPanel.add(currentWeatherScrollPane, BorderLayout.CENTER);

        fiveDayWeatherPanel.removeAll();
        fiveDayWeatherPanel.add(fiveDayWeatherScrollPane, BorderLayout.CENTER);

        airPollutionPanel.removeAll();
        airPollutionPanel.add(airPollutionScrollPane, BorderLayout.CENTER);
       
        // Generating Notification for Poor Weather Conditions
        // --------------------------1) Heavy Rainfall
        if (weather.getRainVol1h() >= 30.0) {
            JOptionPane.showMessageDialog(getWeatherButton, "Heavy rainfall! Rain volume in the past 1 hour: " + weather.getRainVol1h() + " mm", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }
        // --------------------------2) Thunderstorms
        if (weather.getWeatherDescr().equalsIgnoreCase("thunderstorm")) {
            JOptionPane.showMessageDialog(getWeatherButton, "Thunderstorm alert!", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }
        // --------------------------3) Check for extreme heat
        if (weather.getTemperature() > 35.0) {
            JOptionPane.showMessageDialog(getWeatherButton, "Extreme heat warning! Temperature: " + weather.getTemperature() + " C", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }
        // ---------------------------4) Check for extreme cold
        if (weather.getTemperature() < -10.0) {
            JOptionPane.showMessageDialog(getWeatherButton, "Extreme cold warning! Temperature: " + weather.getTemperature() + " C", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }
        // ----------------------------5) Check for snowfall or blizzard
        if (weather.getWeatherDescr().toLowerCase().contains("snow") || weather.getWeatherDescr().toLowerCase().contains("blizzard")) {
            JOptionPane.showMessageDialog(getWeatherButton, "Snowfall/Blizzard alert!", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }
        // ----------------------------6) Check for hurricanes
        if (weather.getWeatherDescr().toLowerCase().contains("hurricane")) {
            JOptionPane.showMessageDialog(getWeatherButton, "Hurricane alert!", "Weather Alert", JOptionPane.WARNING_MESSAGE);
        }

        // Generating Notification for Poor or Very Poor Air Quality Index
        double aqi = weather_inst.getAirPollutionInfo().getAirQualityIndex();
        if (aqi == 4 || aqi == 5) {
            JOptionPane.showMessageDialog(this, "Poor air quality detected! AQI: " + aqi, "Air Quality Alert", JOptionPane.WARNING_MESSAGE);
        }
    
        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

}


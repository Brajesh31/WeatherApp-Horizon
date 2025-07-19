// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            int choicedb = 0;

            //--------------------------------UI MENU
            System.out.println("Choose the UI:");
            System.out.println("[1] Console UI");
            System.out.println("[2] GUI");
            System.out.print("Enter your choice: ");
            do{
                choice = scanner.nextInt();
            }while(choice!=1 && choice!=2);

            //--------------------------------DB MENU
            System.out.println("Choose the Database:");
            System.out.println("[1] SQL Database");
            System.out.println("[2] Text Database");
            System.out.print("Enter your choice: ");
            do{
                choicedb = scanner.nextInt();
            }while(choicedb!=1 && choicedb!=2);
    
            DBManagerInterface db = null;

            switch (choice) {
                case 1: //-------------------------------------------CONSOLE UI
                     
                    if(choicedb == 1) //--------------------SQL DB
                    {
                        try{
                            db = new DBWeatherInstance();
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if(choicedb == 2) //----------------TEXT DB
                    {
                        try{
                            db = new DB2WeatherInstance();
                            
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }

                    new startAppConsole(db);
                    break;

                case 2: //---------------------------------------------------GUI
                    if(choicedb == 1) //--------------------SQL DB
                    {
                        try{
                            db = new DBWeatherInstance();
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if(choicedb == 2) //----------------TEXT DB
                    {
                        try{
                            db = new DB2WeatherInstance();
                            
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                    new startAppGUI(db);
                    break;     
                default:
                    System.out.println("Invalid choice!");
            }
            
            scanner.close();
    }
}

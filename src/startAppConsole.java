public class startAppConsole{
    public startAppConsole(DBManagerInterface obj)
    {
        ConsoleBasedUI consoleUI = new ConsoleBasedUI(obj);
        consoleUI.displayMenu();
    }
}
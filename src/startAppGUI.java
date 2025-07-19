import javax.swing.*;

public class startAppGUI{
    public startAppGUI(DBManagerInterface obj)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppHomepage(obj);
            }
        });
    }
}
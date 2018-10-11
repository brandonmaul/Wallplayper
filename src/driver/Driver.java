package driver;

import data.ProcessData;
import javafx.application.Application;
import model.Model;
import view.View;

public class Driver {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(System.getProperty("os.name").contains("Windows")){
            ProcessData pd = new ProcessData();
            try {
                pd.enableAutoStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Application.launch(View.class, args);
    }
}

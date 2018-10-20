package view;

import model.ProcessData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Wallplayper");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(System.getProperty("os.name").contains("windows")){
            ProcessData pd = new ProcessData();
            try {
                pd.enableAutoStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        launch(args);
    }
}

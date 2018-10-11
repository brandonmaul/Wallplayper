package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        //stage.getIcons().add(new Image("/Resources/AppImages/ImpossibleTriangle.ico"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("../CSS/styleSheet.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Wallplayper");
        stage.setResizable(false);
        //stage.setOpacity(0.5);
        stage.setScene(scene);
        stage.show();
    }

}

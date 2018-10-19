package view;

import javafx.scene.control.Slider;
import model.Model;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;
import javafx.scene.control.ToggleButton;

public class Controller implements Initializable {

    private Model _model;

    /** These are javaFX specific variables, in order to modify an xml element the xml needs to have
     an fx:id which we will reference in code here(Controller.java). In order to manipulate a desired xml element you need
     to make a variable here and name it the **same name as you put in fx:id** make sure to add an '@FXML'  before the vvariable name,
     this lets the controller  java class to link that variable to a specific xml element **/
    @FXML
    private ToggleButton nsfwButton;
    @FXML
    private Slider timeSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _model = new Model();
        configureTimeSlider();
    }

    public void updateNowButtonAction(ActionEvent e){
        _model.setNewWallpaper();
    }

    public void nsfwButtonAction(ActionEvent e){
        if(_model.getNSFWButton()){
            nsfwButton.setText("Disabled");
            _model.toggleNSFWBoolean();
        } else {
            nsfwButton.setText("Enabled");
            _model.toggleNSFWBoolean();
        }
    }

    private void configureTimeSlider(){
        //for setting text on the timeslider
        timeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Minute";
                if (n < 1.5) return "Hour";
                if (n < 2.5) return "Day";
                return "Week";
            }
            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Minute":
                        return 0d;
                    case "Hour":
                        return 1d;
                    case "Day":
                        return 2d;
                    case "Week":
                        return 3d;
                    default:
                        return 1d;
                }
            }
        });
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                _model.setRefreshRate(timeSlider.getValue());
            }
        });
    }
}
package view;

import model.Model;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class Controller implements Initializable {

    private Model _model;
    /*
     * Will be called once on an implementing controller when the contents of its associated document have
     * been completely loaded. This allows the implementing class to perform any necessary post-processing on the content.
     *
     * Also this is called after all @FXML elements have been processed. This means that you can use this function to
     * specify what you want the application to do as soon as it starts up < can be very useful.
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _model = new Model();
    }

    /** These are javaFX specific variables, in order to modify an xml element the xml needs to have
     an fx:id which we will reference in code here(Controller.java). In order to manipulate a desired xml element you need
     to make a variable here and name it the **same name as you put in fx:id** make sure to add an '@FXML'  before the vvariable name,
     this lets the controller  java class to link that variable to a specific xml element **/
    @FXML
    private ToggleButton nsfwButton;


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
}
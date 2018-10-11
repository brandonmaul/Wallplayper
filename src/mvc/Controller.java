package mvc;

import data.ProcessData;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller implements Initializable {



    ProcessData pd = new ProcessData();

    /** These are javaFX specific variables, in order to modify an xml element the xml needs to have
     an fx:id which we will reference in code here(Controller.java). In order to manipulate a desired xml element you need
     to make a variable here and name it the **same name as you put in fx:id** make sure to add an '@FXML'  before the vvariable name,
     this lets the controller  java class to link that variable to a specific xml element **/
    @FXML
    private AnchorPane imgPane, settingsPane, savePane , quickSettMouseOn, quickSettMouseOff, blankPane;
    @FXML
    private ToggleButton nsfwBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button minBtn;



    /* to change the NSFW toggle on and off*/
    private boolean nsfwToggleSelect = true;


    public void imageAction(ActionEvent e) {
        setPaneTo("image");

    }

    public void settingsAction(ActionEvent e){
        setPaneTo("settings");
    }

    public void saveAction(ActionEvent e){
        setPaneTo("save");
    }

    /*
     *  Gets the root node AKA the stage so we can do stuff with the whole application. In this case we want to minimize
     *  the application so we get the root node (stage) and we call setIconified() on our stage which in turn minimizes
     *  teh application.
     */
    public void minimizeAction(ActionEvent e){
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    /*
     *  Gets the root node AKA the stage so we can do stuff with the whole application. In this case we want to close
     *  the application so we get the root node (stage) and we call close() on our stage which in turn minimizes
     *  closes the application.
     */
    public void closeAction(ActionEvent e){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void nsfwBtnAction(ActionEvent e){
        if(nsfwToggleSelect){
            nsfwBtn.setSelected(false);
            nsfwBtn.setText("OFF");
            //_m.toggleNSFWButton();
        } else {
            nsfwBtn.setSelected(false);
            nsfwBtn.setText("ON");
            //_m.toggleNSFWButton();
        }
    }
    @FXML
    public void mouseExitedQuickSettPane(MouseEvent event){
        quickSettMouseOff.setVisible(true);
        quickSettMouseOn.setVisible(false);
    }
    @FXML
    public void mouseEnteredQuickSettPane(MouseEvent event){
        quickSettMouseOff.setVisible(false);
        quickSettMouseOn.setVisible(true);
    }
    @FXML
    public void  settingsAction(){
        setPaneTo("settings");
    }

    /* This function simply switches between AnchorPanes to display to the user
     * Parameters: a string to choose what Pane you want to display to the user
     *             if you put an invalid string it will go to a default blank screen.
     *
     *
     * this function will need to be modified as we add more features to our project.
     * */

    private void setPaneTo(String s) {

        quickSettMouseOn.setVisible(true);
        quickSettMouseOff.setVisible(false);

        if(s.equalsIgnoreCase("image")){
            imgPane.setVisible(true);
            settingsPane.setVisible(false);
            savePane.setVisible(false);
            blankPane.setVisible(false);
        } else if (s.equalsIgnoreCase("settings")){
            imgPane.setVisible(false);
            settingsPane.setVisible(true);
            savePane.setVisible(false);
            blankPane.setVisible(false);
        } else if (s.equalsIgnoreCase("save")) {
            imgPane.setVisible(false);
            settingsPane.setVisible(false);
            savePane.setVisible(true);
            blankPane.setVisible(false);
        } else if (s.equalsIgnoreCase("minimize")) {
            imgPane.setVisible(false);
            settingsPane.setVisible(false);
            savePane.setVisible(false);
            blankPane.setVisible(false);
        } else if(s.equalsIgnoreCase("close")){
            imgPane.setVisible(false);
            settingsPane.setVisible(false);
            savePane.setVisible(false);
            blankPane.setVisible(false);
        } else {
            imgPane.setVisible(false);
            settingsPane.setVisible(false);
            savePane.setVisible(false);
            blankPane.setVisible(true);
        }
    }


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
        // TODO
        setPaneTo("");
        quickSettMouseOff.setVisible(true);
        quickSettMouseOn.setVisible(false);
    }
}
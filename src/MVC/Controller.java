package MVC;

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

    /** These are javaFX specific variables, in order to modify an xml element the xml needs to have
        an fx:id which we will reference in code here(Controller.java). In order to manipulate a desired xml element you need
        to make a variable here and name it the **same name as you put in fx:id** make sure to add an '@FXML'  before the vvariable name,
        this lets the controller  java class to link that variable to a specific xml element **/
    @FXML
    private AnchorPane img_pane, sett_pane, save_pane, blank_pane, quick_sett_mouse_on, quick_sett_mouse_off;
    @FXML
    private ToggleButton nsfw_btn;
    @FXML
    private Button close_btn1;
    @FXML
    private Button min_btn1;

    /* to change the NSFW toggle on and off*/
    private boolean nsfw_toggle_select = true;


    public void image_action() {
        set_pane_to("image");

    }

    public void settings_action(){
        set_pane_to("settings");
    }

    public void save_action(){
        set_pane_to("save");
    }

    /*
    *  Gets the root node AKA the stage so we can do stuff with the whole application. In this case we want to minimize
    *  the application so we get the root node (stage) and we call setIconified() on our stage which in turn minimizes
    *  teh application.
    */
    public void minimize_action(){
        Stage stage = (Stage) min_btn1.getScene().getWindow();
        stage.setIconified(true);
    }

    /*
    *  Gets the root node AKA the stage so we can do stuff with the whole application. In this case we want to close
    *  the application so we get the root node (stage) and we call close() on our stage which in turn minimizes
    *  closes the application.
    */
    public void close_action(){
        Stage stage = (Stage) close_btn1.getScene().getWindow();
        stage.close();
    }

    public void nsfw_action(ActionEvent e){
        if(nsfw_toggle_select){
            nsfw_btn.setSelected(false);
            nsfw_btn.setText("OFF");
            nsfw_toggle_select = false;
        } else {
            nsfw_btn.setSelected(false);
            nsfw_btn.setText("ON");
            nsfw_toggle_select = true;
        }
    }
    @FXML
    public void mouseExeitedQuickSettPane(MouseEvent event){
        quick_sett_mouse_off.setVisible(true);
        quick_sett_mouse_on.setVisible(false);
    }
    @FXML
    public void mouseEnterdQuickSettPane(MouseEvent event){
        quick_sett_mouse_off.setVisible(false);
        quick_sett_mouse_on.setVisible(true);
    }
    @FXML
    public void  settings_clicked(){
        set_pane_to("settings");
    }

    /* Create function to get weather */


    /* This function simply switches between AnchorPanes to display to the user

    * Parameters: a string to choose what Pane you want to display to the user
    *             if you put an invalid string it will go to a default blank screen.
    *
    *
    * this function will need to be modified as we add more features to our project.
    * */

    private void set_pane_to(String s){

        quick_sett_mouse_on.setVisible(true);
        quick_sett_mouse_off.setVisible(false);

        if(s.equalsIgnoreCase("image")){
            img_pane.setVisible(true);
            sett_pane.setVisible(false);
            save_pane.setVisible(false);
        } else if (s.equalsIgnoreCase("settings")){
            img_pane.setVisible(false);
            sett_pane.setVisible(true);
            save_pane.setVisible(false);
        } else if (s.equalsIgnoreCase("save")) {
            img_pane.setVisible(false);
            sett_pane.setVisible(false);
            save_pane.setVisible(true);
        } else if (s.equalsIgnoreCase("minimize")) {
            img_pane.setVisible(false);
            sett_pane.setVisible(false);
            save_pane.setVisible(false);
        } else if(s.equalsIgnoreCase("close")){
            img_pane.setVisible(false);
            sett_pane.setVisible(false);
            save_pane.setVisible(false);
        } else {
            img_pane.setVisible(false);
            sett_pane.setVisible(false);
            save_pane.setVisible(false);
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
        set_pane_to("");
        quick_sett_mouse_off.setVisible(true);
        quick_sett_mouse_on.setVisible(false);

    }
}

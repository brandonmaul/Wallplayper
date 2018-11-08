package view;

import com.sun.deploy.util.StringUtils;
import javafx.scene.control.*;
import model.CustomTimer;
import model.Model;


import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;

public class Controller implements Initializable {

    private Model _model;

    private CustomTimer _timer;

    /** These are javaFX specific variables, in order to modify an xml element the xml needs to have
     an fx:id which we will reference in code here(Controller.java). In order to manipulate a desired xml element you need
     to make a variable here and name it the **same name as you put in fx:id** make sure to add an '@FXML'  before the vvariable name,
     this lets the controller  java class to link that variable to a specific xml element **/
    @FXML
    private ToggleButton nsfwButton;
    @FXML
    private Slider timeSlider;
    @FXML
    private ListView<String> subredditLV;
    @FXML
    private Button deleteSubButton;
    @FXML
    private Button saveButton;
    @FXML
    private ProgressIndicator progressBar;
    @FXML
    private Button updateNowButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _model = new Model(this);
        configureSubredditLV();
        configureTimeSlider();
    }

    private void configureSubredditLV() {
        subredditLV.setItems(_model.getSubreddits());
        subredditLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        subredditLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    deleteSubButton.setDisable(false);
                }else{
                    deleteSubButton.setDisable(true);
                }
            }
        });
    }

    public void addSubButtonAction(){
        TextInputDialog dialog = new TextInputDialog("wallpapers");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Add Subreddit");
        dialog.setContentText("www.reddit.com/r/");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            subredditLV.getItems().add(result.get());
            subredditLV.refresh();
            _model.setExtractorNeedsReloading(true);
        }
    }

    public void deleteSubButtonAction(){
        subredditLV.getItems().remove(subredditLV.getSelectionModel().getSelectedIndex());
        _model.setExtractorNeedsReloading(true);
    }

    public void updateNowButtonAction(){
        updateNowButton.setDisable(true);
        progressBar.setProgress(0.0);
        progressBar.setVisible(true);
        boolean bool = true;
        if(_model.getExtractorReloadBoolean()){
            bool = _model.reloadSubs();
        }
        progressBar.setProgress(.9);
        if(bool){
            _model.setNewWallpaper();
        }
        progressBar.setProgress(1.0);
        progressBar.setVisible(false);
        updateNowButton.setDisable(false);

    }

    public void saveButtonAction(){
        try {
            Properties properties = new Properties();
            properties.setProperty("NSFWAllowed", Boolean.toString(_model.isNSFWAllowed()));
            properties.setProperty("RefreshRate", Double.toString(_model.getRefreshRate()));
            properties.setProperty("SubList", StringUtils.join(_model.getSubreddits(), ","));

            File file = new File(_model.getDownloadFolder()+"Wallplayper.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Settings");
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _timer.start();
    }

    public void nsfwButtonAction(){
        if(!_model.isNSFWAllowed()){
            nsfwButton.setText("Disabled");
            _model.toggleNSFWBoolean();
            _model.setExtractorNeedsReloading(true);
        } else {
            nsfwButton.setText("Enabled");
            _model.toggleNSFWBoolean();
            _model.setExtractorNeedsReloading(true);
        }
    }

    private void configureTimeSlider(){
        timeSlider.setValue(_model.getRefreshRate());
        timeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Manually";
                if (n < 1.5) return "Minute";
                if (n < 2.5) return "Hour";
                return "Day";
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

        _timer = new CustomTimer(_model, this);
        _timer.start();
    }

    public void updateProgressBar(Double d){
        progressBar.setProgress(progressBar.getProgress()+d);
    }

}
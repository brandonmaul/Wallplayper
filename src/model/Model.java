package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _extractorNeedsReloading;
    private boolean _NSFWAllowed;
    private double _refreshRate; //Double for storing the refresh time of the program (double will be from 0.0 - 3.0)
    private static ObservableList<String> _subreddits = FXCollections.observableArrayList();;

    public Model(){
        this.load();
        _extractorNeedsReloading = false;
        _extractor = new Extractor(this);
        _wallpaperUtil = new WallpaperUtility();
    }

    private void load(){
        try {
            File file = new File("Wallplayper.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            this._NSFWAllowed = Boolean.parseBoolean(properties.getProperty("NSFWAllowed"));
            this._refreshRate = Double.parseDouble(properties.getProperty("RefreshRate"));
            this._subreddits.clear();
            for(String s : properties.getProperty("SubList").split(",")) {
                this._subreddits.add(s);
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void setNewWallpaper(){
        File file = _extractor.get();
        if(file != null){
            _wallpaperUtil.setWallpaper(file);
        }
    }

    public void setRefreshRate(double d){
        _refreshRate = d;
    }

    public static ObservableList<String> getSubreddits(){
        return _subreddits;
    }

    public boolean isNSFWAllowed(){
        return _NSFWAllowed;
    }

    public void toggleNSFWBoolean(){
        _NSFWAllowed = !_NSFWAllowed;
    }

    public boolean getExtractorReloadBoolean(){
        return _extractorNeedsReloading;
    }

    public void reloadSubs(){
        _extractor.load();
        setExtractorNeedsReloading(false);
    }

    public void setExtractorNeedsReloading(boolean b){
        _extractorNeedsReloading = b;
    }

    public double getRefreshRate() {
        return _refreshRate;
    }
}

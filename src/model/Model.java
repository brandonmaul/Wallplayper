package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Properties;

public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _extractorNeedsReloading;
    private boolean _NSFWAllowed;
    private double _refreshRate; //Double for storing the refresh time of the program (double will be from 0.0 - 3.0)
    private static ObservableList<String> _subreddits = FXCollections.observableArrayList();;


    public static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "\\Wallplayper\\";
    public static final String DOWNLOAD_FOLDER_MAC = System.getProperty("user.home") + "/Library/Application Support/Wallplayper/";
    public static final String DOWNLOAD_FOLDER_LINUX = System.getProperty("user.home") + "/Wallplayper/";

    public Model(){
        this.load();
        _extractorNeedsReloading = false;
        _extractor = new Extractor(this);
        _wallpaperUtil = new WallpaperUtility();
    }

    private void load(){
        try {
            Properties properties = new Properties();
            File file = new File(getDownloadFolder()+"Wallplayper.properties");
            FileInputStream fileInput = new FileInputStream(file);
            if(fileInput.available() > 0){
                properties.load(fileInput);
                fileInput.close();
            }else{
                fileInput.close();
                properties.setProperty("NSFWAllowed", Boolean.toString(false));
                properties.setProperty("RefreshRate", Double.toString(1.0));
                properties.setProperty("SubList", "wallpapers,skyrimporn,earthporn");

                FileOutputStream fileOut = new FileOutputStream(file);
                properties.store(fileOut, "Settings");
                fileOut.close();
            }

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

    public String getDownloadFolder(){
        if(System.getProperty("os.name").startsWith("Mac")){
            return DOWNLOAD_FOLDER_MAC;
        }else if(System.getProperty("os.name").startsWith("Windows")){
            return DOWNLOAD_FOLDER_WINDOWS;
        }else{
            return null;
        }
    }
}

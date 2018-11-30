package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.Controller;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private File _downloadFolder;
    private File _lastImage;
    private boolean _extractorNeedsReloading;
    private boolean _NSFWAllowed;
    private double _refreshRate; //Double for storing the refresh time of the program (double will be from 0.0 - 3.0)
    private static ObservableList<String> _subreddits = FXCollections.observableArrayList();
    ;

    public static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "\\Wallplayper\\";
    public static final String DOWNLOAD_FOLDER_MAC = System.getProperty("user.home") + "/Library/Application Support/Wallplayper/";
    public static final String DOWNLOAD_FOLDER_LINUX = System.getProperty("user.home") + "/Wallplayper/";


    public Model(Controller c) {
        this.load();
        _extractorNeedsReloading = false;
        _extractor = new Extractor(this, c);
        _wallpaperUtil = new WallpaperUtility();
    }

    private void load() {
        try {
            Properties properties = new Properties();
            File file = new File(getSystemApplicationFolder() + "Wallplayper.properties");
            if (file.exists()) {
                FileInputStream fileInput = new FileInputStream(file);
                properties.load(fileInput);
                if (properties.getProperty("SubList").isEmpty()) {
                    properties.setProperty("SubList", "wallpapers,cityporn,earthporn,foodporn");
                }
                fileInput.close();
            } else {
                //DEFAULT PROPERTIES
                properties.setProperty("NSFWAllowed", Boolean.toString(false));
                properties.setProperty("RefreshRate", Double.toString(0.0));
                properties.setProperty("SubList", "wallpapers,cityporn,earthporn,foodporn");
                properties.setProperty("DLLocation", getSystemApplicationFolder());

                FileOutputStream fileOut = new FileOutputStream(file);
                properties.store(fileOut, "Settings");
                fileOut.close();
            }

            this._NSFWAllowed = Boolean.parseBoolean(properties.getProperty("NSFWAllowed"));
            this._refreshRate = Double.parseDouble(properties.getProperty("RefreshRate"));
            this._downloadFolder = new File(properties.getProperty("DLLocation"));
            this._subreddits.clear();
            for (String s : properties.getProperty("SubList").split(",")) {
                this._subreddits.add(s);
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void setNewWallpaper() {
        File file = _extractor.get();
        if (file != null) {
            if (_lastImage != null) {
                this._lastImage.delete();
            }
            _wallpaperUtil.setWallpaper(file);
            this._lastImage = file;
        }
    }

    public void setRefreshRate(double d) {
        _refreshRate = d;
    }

    public static ObservableList<String> getSubreddits() {
        return _subreddits;
    }

    public boolean isNSFWAllowed() {
        return _NSFWAllowed;
    }

    public void toggleNSFWBoolean() {
        _NSFWAllowed = !_NSFWAllowed;
    }

    public boolean getExtractorReloadBoolean() {
        return _extractorNeedsReloading;
    }

    public boolean reloadSubs() {
        boolean bool = _extractor.load();
        setExtractorNeedsReloading(!bool);
        return bool;
    }

    public void setExtractorNeedsReloading(boolean b) {
        _extractorNeedsReloading = b;
    }

    public double getRefreshRate() {
        return _refreshRate;
    }

    public String getSystemApplicationFolder() {
        if (System.getProperty("os.name").startsWith("Mac")) {
            return DOWNLOAD_FOLDER_MAC;
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            return DOWNLOAD_FOLDER_WINDOWS;
        } else {
            return null;
        }
    }

    public File getDownloadFolder() {
        return _downloadFolder;
    }

    public void setDownloadFolder(File f) {
        _downloadFolder = f;
    }

    public String downloadWallpaper() {File image = getLastImage();
        if(image != null){
            File newLocation = new File(getDownloadFolder() + File.separator + image.getName());
            if(!newLocation.exists()){ //prevents duplication
                try {
                    Files.copy(image.toPath(), newLocation.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "File moved successfully";
        }else{
            return "Failed to move the file";
        }
    }

    public File getLastImage(){
        return _lastImage;
    }
}

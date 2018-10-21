package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _extractorNeedsReloading;
    private boolean _NSFWAllowed;
    private double _refreshRate; //Double for storing the refresh time of the program (double will be from 0.0 - 3.0)
    private static ObservableList<String> _subreddits;

    public Model(){
        _subreddits = FXCollections.observableArrayList();
        _subreddits.add("wallpapers");
        _extractorNeedsReloading = false;
        _NSFWAllowed = false;
        _extractor = new Extractor(this);
        _wallpaperUtil = new WallpaperUtility();
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
}

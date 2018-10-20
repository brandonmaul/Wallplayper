package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.io.File;
public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _NSFWAllowed = false;
    private double _refreshRate; //hours?
    private static ObservableList<String> _subreddits;

    public Model(){
        _subreddits = FXCollections.observableArrayList();

        _extractor = new Extractor(this);
        _wallpaperUtil = new WallpaperUtility();
    }

    public void setNewWallpaper(){
        File file = _extractor.get();
        _wallpaperUtil.setWallpaper(file);
    }

    public void setRefreshRate(double d){
        _refreshRate = d;
    }

    public void addSub(String s){
        _subreddits.add(s);
    }
    public static ObservableList<String> getSubreddits(){
        return _subreddits;
    }

    public boolean getNSFWBoolean(){
        return _NSFWAllowed;
    }

    public void toggleNSFWBoolean(){
        _NSFWAllowed = !_NSFWAllowed;
    }
}

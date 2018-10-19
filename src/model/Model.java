package model;

import java.util.ArrayList;
import java.io.File;
public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _NSFWAllowed = false;
    private double _refreshRate; //hours?
    private static ArrayList<String> _subreddits;

    public Model(){
        _subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        _subreddits.add("wallpapers");

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

    public static ArrayList<String> getSubreddits(){
        return _subreddits;
    }

    public boolean getNSFWBoolean(){
        return _NSFWAllowed;
    }

    public void toggleNSFWBoolean(){
        _NSFWAllowed = !_NSFWAllowed;
    }
}

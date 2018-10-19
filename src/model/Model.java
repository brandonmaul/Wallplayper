package model;

import java.util.ArrayList;
import java.io.File;
public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _NSFWEnabled;
    private double _refreshRate; //hours?
    private static ArrayList<String> _subreddits;

    public Model(){
        _NSFWEnabled = true;
        _subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        _subreddits.add("wallpapers");

        _extractor = new Extractor();
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

    public boolean getNSFWButton(){
        return _NSFWEnabled;
    }

    public void toggleNSFWBoolean(){
        _NSFWEnabled = !_NSFWEnabled;
    }
}

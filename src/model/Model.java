package model;

import java.util.ArrayList;
import java.io.File;
public class Model {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _NSFWEnabled;
    private static ArrayList<String> subreddits;

    public Model(){
        _NSFWEnabled = true;
        subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        subreddits.add("wallpapers");

        _extractor = new Extractor();
        _wallpaperUtil = new WallpaperUtility();
    }

    public void setNewWallpaper(){
        File file = _extractor.get();
        _wallpaperUtil.setWallpaper(file);
    }

    public static ArrayList<String> getSubreddits(){
        return subreddits;
    }

    public boolean getNSFWButton(){
        return _NSFWEnabled;
    }

    public void toggleNSFWBoolean(){
        _NSFWEnabled = !_NSFWEnabled;
    }
}

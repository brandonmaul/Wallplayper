package data;

import java.util.ArrayList;
import java.io.File;
public class Data {

    private Extractor _extractor;
    private WallpaperUtility _wallpaperUtil;

    private boolean _NSFWButton;
    private static ArrayList<String> subreddits;

    public Data(){
        _NSFWButton = true;
        subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        subreddits.add("wallpapers");

        _extractor = new Extractor();
        _wallpaperUtil = new WallpaperUtility();
    }

    public void downloadNewImage(){
        File file = _extractor.get();
        _wallpaperUtil.setWallpaper(file);
    }

    public static ArrayList<String> getSubreddits(){
        return subreddits;
    }

    public boolean getNSFWButton(){
        return _NSFWButton;
    }

    public void toggleNSFWButton(){
        _NSFWButton = !_NSFWButton;
    }
}

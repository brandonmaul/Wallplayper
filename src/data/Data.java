package data;

import java.util.ArrayList;

public class Data {

    private Extractor extractor;

    private boolean _NSFWButton;
    private static ArrayList<String> subreddits;

    public Data(){
        _NSFWButton = true;
        subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        subreddits.add("wallpapers");
        subreddits.add("cityporn");
        subreddits.add("earthporn");
        subreddits.add("foodporn");

        extractor = new Extractor();
    }

    public void downloadNewImage(){
        extractor.get();
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

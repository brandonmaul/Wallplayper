package data;

import java.util.ArrayList;

public class Data {

    private Extractor extractor;

    private boolean _NSFWButton;
    private static ArrayList<String> subreddits;

    public Data(){
        extractor = new Extractor(this);
        _NSFWButton = true;
        subreddits = new ArrayList<>();
        //TEST CASE... Remove the next line.
        subreddits.add("wallpapers");


    }

    public void downloadNewImage(){
        extractor.run();
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

package model;

import java.util.ArrayList;

public class Model {
    private Extractor extractor;

    private boolean _NSFWButton;
    private static ArrayList<String> subreddits;

    public Model(){
        extractor = new Extractor(this);
        _NSFWButton = true;
        subreddits = new ArrayList<>();
        //TEST CASE... Remove the next two lines.
        subreddits.add("wallpapers");
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

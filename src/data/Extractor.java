package data;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import java.util.Random;
import java.io.IOException;




/**
 * Parses through a json file on a certain subreddit, and grab direct links to images.
 */
public class Extractor {

    public static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "/Wallplayer/";
    public static final String DOWNLOAD_FOLDER_MAC = System.getProperty("user.home") + "/library/Application Support/Wallplayer/";
    public static final String DOWNLOAD_FOLDER_LINUX = System.getProperty("user.home") + "/Wallplayper/";

    private List<String> subreddits; // subreddits to scan through
    private List<String> imageLinks; // links to all images found
    private String finalLink;

    public Extractor(){
        this.load();
    }

    public void load(){
        subreddits = Data.getSubreddits();
        imageLinks = new ArrayList<>();
        imageLinks = look();
    }

    //Load must be called first before this command
    public void get(){
        //returns a single link from the array of pulled wallpapers, at random.
        finalLink = imageLinks.get(new Random().nextInt(imageLinks.size()));

        //saves image into history folder with unique filename
        String[] parts = finalLink.split("/");
        String lastPart = parts[parts.length-1];
        try {
            InputStream inputstream = new URL(finalLink).openStream();
            Files.copy(inputstream, Paths.get("history/"+lastPart));
        } catch (IOException e) {
        }
    }

    /*
     * For each subreddit specified, do a search
     */
    public List<String> look() {
        for(String subreddit : subreddits) {
            //System.out.println("Scanning: " + subreddit);

            // set the address
            String firstPart = "https://reddit.com/r/";
            String fullLink = firstPart + subreddit + "/hot.json";


            // now perform a scan for this subreddit
            String jsonString = readJSONFromURL(fullLink);
            JSONObject jason = new JSONObject(jsonString);
            JSONArray children = jason.getJSONObject("data").getJSONArray("children");

            for(int i = 0; i < children.length(); i++) {
                String link = (children.getJSONObject(i).getJSONObject("data").getString("url"));
                if(link.contains(".jpg") | link.contains(".jpeg") | link.contains(".png"))
                    imageLinks.add(link);
            }
        }

        return imageLinks;
    }


    /**
     * Reads the json file from the given url
     */
    private String readJSONFromURL(String urlString) {
        //System.out.println("Reading JSON from " + urlString);
        StringBuilder sb = new StringBuilder();
        URLConnection uc = null;
        InputStreamReader in = null;

        try {
            URL url = new URL(urlString);
            uc = url.openConnection();

            Thread.sleep(2000); // to comply with reddit's rate-limiting rules
            uc.setRequestProperty("User-Agent", "Wallplayper"); // ^ same here. Please dont change.

            if(uc != null)
                uc.setReadTimeout(60 * 1000);

            if(uc != null && uc.getInputStream() != null) {
                in = new InputStreamReader(uc.getInputStream(),Charset.defaultCharset());
                BufferedReader br = new BufferedReader(in);
                if(br != null) {
                    int cp;
                    while((cp = br.read()) != -1) {
                        sb.append((char) cp);
                    }
                    br.close();
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
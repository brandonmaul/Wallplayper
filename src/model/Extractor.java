package model;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Parses through a json file on a certain subreddit, and grab direct links to images.
 */
public class Extractor {

    public static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "/Wallplayer/";
    public static final String DOWNLOAD_FOLDER_MAC = System.getProperty("user.home") + "/library/Application Support/Wallplayer/";
    public static final String DOWNLOAD_FOLDER_LINUX = System.getProperty("user.home") + "/Wallplayper/";

    private Model _m;
    private List<String> subreddits; // subreddits to scan through
    private List<String> imageLinks; // links to all images found
    private String finalLink;

    public Extractor(Model m) {
        _m = m;
    }

    public void run(){
        subreddits = Model.getSubreddits();
        imageLinks = new ArrayList<>();
        finalLink = look();

    }

    /*
     * For each subreddit specified, do a search
     */
    public String look() {
        for(String subreddit : subreddits) {
            //System.out.println("Scanning: " + subreddit);

            // set the address
            String baseLink = "https://reddit.com/r/";
            String fullLink = baseLink + subreddit + "/hot.json";

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

        //returns a single link from the array of pulled wallpapers, at random.
        return imageLinks.get(new Random().nextInt(imageLinks.size()));

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
            uc.setRequestProperty("User-Agent", "Wallplayer"); // ^ same here. Please dont change.

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

    private File download(String fileUrl, String fileDest) throws IOException {
        URL url = new URL(fileUrl);
        BufferedInputStream input = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int n = 0;
        while(-1 != (n = input.read(buf)))
            output.write(buf, 0, n);
        output.close();
        input.close();

        byte[] response = output.toByteArray();
        File file = new File(fileDest);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(response);
        fos.close();

        return file;
    }

}
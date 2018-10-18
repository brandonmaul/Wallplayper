package data;
import java.util.*;
import java.io.File;

public class WallpaperUtility {

    private String _os;

    public WallpaperUtility(){
        _os = System.getProperty("os.name");
    }

    public void setWallpaper(File file) {

        if (_os.startsWith("Windows")) { //WINDOWS

        } else if (_os.startsWith("Mac")) { //MAC

            Runtime runtime = Runtime.getRuntime();
            String command = "tell application \"System Events\"\n"
                    +"tell current desktop\n"
                    +"set picture to \""+file.getAbsolutePath()+"\"\n"
                    +"end tell\n"
                    +"end tell";

            String[] args = { "osascript", "-e", command};
            try{
                Process p = runtime.exec(args);
            }catch (Exception e){

            }
        } else {
            System.out.println("OS Not supported/found");
        }


    }
}

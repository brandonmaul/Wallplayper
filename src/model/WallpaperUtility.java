package model;


import java.io.File;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;



public class WallpaperUtility {

    private String _os;

    public WallpaperUtility(){
        _os = System.getProperty("os.name");
    }

    public static String getLatestFilefromDir(String dirPath){
        File dir = new File("C:\\Users\\Jasmeet Kaur Chawla\\AppData\\Roaming\\Wallplayper");
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        String name = lastModifiedFile.getName();
        return name;
    }
    public static interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32",User32.class,W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }

    public void setWallpaper(File file) {
        if (_os.startsWith("Windows")) { //WINDOWS
            String command1 = "powershell.exe Set-ItemProperty -path 'HKCU:\\Control Panel\\Desktop\\' -name wallpaper -value \"" + file.getAbsolutePath() + "\"";
            //Find the path to where the images are getting downloaded
            try{
                Runtime.getRuntime().exec(command1);
                String path = "C:\\Users\\Jasmeet Kaur Chawla\\AppData\\Roaming\\Wallplayper"; //Change the path here
                String wallpaper= "C:\\Users\\Jasmeet Kaur Chawla\\AppData\\Roaming\\Wallplayper\\"+ getLatestFilefromDir(path); //add the path back here

                User32.INSTANCE.SystemParametersInfo(0x0014, 0, wallpaper , 1);
            }


            catch (Exception e){
            }

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

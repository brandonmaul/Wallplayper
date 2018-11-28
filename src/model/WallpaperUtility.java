package model;


import java.io.File;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import java.io.FileFilter;



public class WallpaperUtility {
    private static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "\\Wallplayper\\";


    private String _os;

    public WallpaperUtility(){
        _os = System.getProperty("os.name");
    }

    public static String getLatestFilefromDir(String dirPath){
        File dir = new File(DOWNLOAD_FOLDER_WINDOWS);
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

    public File setWallpaper(File file) {
        if (_os.startsWith("Windows")) { //WINDOWS
            try {
                User32.INSTANCE.SystemParametersInfo(0x0014, 0, file.getAbsolutePath(), 1);
                Thread.sleep(3000);
                file.delete();
            }

            catch (Exception e){
                System.out.println("WallpaperUtil.setWallpaper failed");
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
                while(p.isAlive()){
                }

            }catch (Exception e){
                System.out.println("WallpaperUtil.setWallpaper failed");
            }
        } else {
            System.out.println("OS Not supported/found");
        }

        return file;
    }

    // Gets the last modified file.
    public static File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }



    public String getWallpaper(String Folder){

        String home = System.getProperty("user.home");

        File file_try = lastFileModified(home+"/Library/Application Support/Wallplayper/");
        if (file_try.getName().contains(".png")||file_try.getName().contains(".jpg")) {
            if (file_try.renameTo
                    (new File(Folder + "/" + file_try.getName()))) {
                file_try.delete();
                return "File moved successfully";
            }
            else
            {
                return "Failed to move the file";
            }
        }
        else
        {
            return "Failed to move the file";
        }
    }
}

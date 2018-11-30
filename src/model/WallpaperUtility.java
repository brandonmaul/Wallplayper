package model;


import java.io.File;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;


public class WallpaperUtility {
    private static final String DOWNLOAD_FOLDER_WINDOWS = System.getenv("APPDATA") + "\\Wallplayper\\";


    private String _os;
    public WallpaperUtility() {
        _os = System.getProperty("os.name");
    }

    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(int one, int two, String s, int three);
    }

    public File setWallpaper(File file) {
        if (_os.startsWith("Windows")) { //WINDOWS
            try {
                User32.INSTANCE.SystemParametersInfo(0x0014, 0, file.getAbsolutePath(), 1);
            } catch (Exception e) {
                System.out.println("WallpaperUtil.setWallpaper failed");
            }

        } else if (_os.startsWith("Mac")) { //MAC

            Runtime runtime = Runtime.getRuntime();
            String command = "tell application \"System Events\"\n"
                    + "tell current desktop\n"
                    + "set picture to \"" + file.getAbsolutePath() + "\"\n"
                    + "end tell\n"
                    + "end tell";

            String[] args = {"osascript", "-e", command};
            try {
                runtime.exec(args);

            } catch (Exception e) {
                System.out.println("WallpaperUtil.setWallpaper failed");
            }
        } else {
            System.out.println("OS Not supported/found");
        }

        return file;
    }
}

package data;

import java.io.File;


public class WallpaperUtility {

    private String _os;

    public WallpaperUtility(){
        _os = System.getProperty("os.name");
    }

    public void setWallpaper(File file) {
        if (_os.startsWith("Windows")) { //WINDOWS
            String command1 = "powershell.exe Set-ItemProperty -path 'HKCU:\\Control Panel\\Desktop\\' -name wallpaper -value \"" + file.getAbsolutePath() + "\"";
            String command2 = "powershell.exe rundll32.exe user32.dll, UpdatePerUserSystemParameters, 1, True";
            try{
                Runtime.getRuntime().exec(command1);
                Runtime.getRuntime().exec(command2);
            }catch (Exception e){
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

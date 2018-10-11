package data;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessData {


    public void startExe() throws Exception {


        //gets program.exe from inside the JAR file as an input stream
        InputStream is = getClass().getResource("/Windows/registry/regAddAutostart.exe").openStream();
        //sets the output stream to a system folder
        OutputStream os = new FileOutputStream("regAddAutostart.exe");


        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();


        //String filepath = "C:/AutoStart.exe";
        Process p = Runtime.getRuntime().exec("regAddAutostart.exe");
        p.waitFor();

    }
}

package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessData {


    public void enableAutoStart() throws Exception {


        File dir = new File(".temp");
        boolean success = dir.mkdir();
        if(!success) {
            System.out.println("Error in creating '.temp' folder");
            //return;
        }

        //gets program.exe from inside the JAR file as an input stream
        InputStream is = getClass().getResource("/windows/registry/enableAutoStart.exe").openStream();

        //sets the output stream to a system folder
        OutputStream os = new FileOutputStream(".temp/temp.exe");


        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }


        is.close();
        os.close();

        //String filepath = "C:/AutoStart.exe";
        Process p = Runtime.getRuntime().exec(".temp/temp.exe");
        p.waitFor();
        deleteFolder(dir);



    }

    public void  disableAutoStart() throws Exception {

        File dir = new File(".temp");
        boolean success = dir.mkdir();
        if(!success) {
            System.out.println("Error in creating '.temp' folder");
            //return;
        }

        //gets program.exe from inside the JAR file as an input stream
        InputStream is = getClass().getResource("/windows/registry/disableAutoStart.exe").openStream();
        //sets the output stream to a system folder
        OutputStream os = new FileOutputStream(".temp/temp.exe");


        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();

        //String filepath = "C:/AutoStart.exe";
        Process p = Runtime.getRuntime().exec(".temp/temp.exe");
        p.waitFor();
        deleteFolder(dir);



    }

     protected void deleteFolder(File folder){
        boolean success= true;
        File[] filesInFolder = folder.listFiles();
        if(filesInFolder != null) { // some JVMs return null for empty folders
            for(File f : filesInFolder){

                if(f.isDirectory()) {
                    deleteFolder(f);
                }else {
                    success = f.delete();
                    if(!success) {
                        System.out.println("Could not delete a file in folder");
                        //return;
                    }
                }
            }
        }
        success = folder.delete();

        if(!success) System.out.println("could not delete folder");
    }
}

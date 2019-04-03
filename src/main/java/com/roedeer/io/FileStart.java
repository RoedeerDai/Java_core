package com.roedeer.io;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 2/25/2019 4:13 PM
 **/
public class FileStart {


    public static void copyFileByStream(File source, File dest) {
        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.ws.apple.ayuep.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by apple on 16/7/30.
 */

public class ImageUtil {
    public static void compressBmpToFile(String url, String targetPath){
        Bitmap bmp = BitmapFactory.decodeFile(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 10;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        baos.reset();
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        File outputFile=new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

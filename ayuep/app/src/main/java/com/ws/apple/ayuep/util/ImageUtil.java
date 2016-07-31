package com.ws.apple.ayuep.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
        int options = 60;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
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

//    public static String compressImage(String filePath, String targetPath, int quality)  {
//        Bitmap bm = getSmallBitmap(filePath);
//        //旋转照片角度，省略
//        /*int degree = readPictureDegree(filePath);
//        if(degree!=0){
//            bm=rotateBitmap(bm,degree);
//        }*/
//        File outputFile=new File(targetPath);
//        try {
//            if (!outputFile.exists()) {
//                outputFile.getParentFile().mkdirs();
//                //outputFile.createNewFile();
//            }else{
//                outputFile.delete();
//            }
//            FileOutputStream out = new FileOutputStream(outputFile);
//            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
//        }catch (Exception e){}
//        return outputFile.getPath();
//    }
//
//    /**
//     * 根据路径获得突破并压缩返回bitmap用于显示
//     */
//    public static Bitmap getSmallBitmap(String filePath) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
//        BitmapFactory.decodeFile(filePath, options);
//        // 计算缩放比
//        options.inSampleSize = calculateInSampleSize(options, 480, 800);
//        // 完整解析图片返回bitmap
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(filePath, options);
//    }
}

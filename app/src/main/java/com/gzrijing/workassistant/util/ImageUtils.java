package com.gzrijing.workassistant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    /**
     * 将图片保存到SD中
     */
    public static void saveFile(Context context, Bitmap bm, String fileName) throws IOException {
        File path = getImagePath(context);
        if(path==null){
            return;
        }
        // 图片文件如果不存在创建之
        File myCaptureFile = new File(path, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        // 将图片压缩至文件对应的流里,即保存图片至该文件中
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
    }

    /**
     * 获取图片存储文件夹路径
     */
    private static File getImagePath(Context context){
        // 未安装SD卡时不做保存
        String storageState = Environment.getExternalStorageState();
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showToast(context, "未检测到SD卡", Toast.LENGTH_SHORT);
            return null;
        }

        // 图片文件保存路径
        File storageDirectory = Environment.getExternalStorageDirectory();
        File path = new File(storageDirectory, "/GZRJWorkassistant/image");
        // 图片路径不存在创建之
        if (!path.exists()) {
            path.mkdirs();
        }

        return path;
    }

    /**
     * 获取图片并显示
     */
    public static void displayImage(Context context, String picUrl, ImageView imageView){
        File path = ImageUtils.getImagePath(context);
        if(path==null){
            return;
        }
        String url = path.getPath() + "/" + picUrl;
        String imageUrl = ImageDownloader.Scheme.FILE.wrap(url);
        imageLoader.displayImage(imageUrl, imageView);
    }

}

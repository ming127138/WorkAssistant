package com.gzrijing.workassistant.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtil {

    public static Bitmap handleBitmap(Bitmap bitmap, float targetWidth, float targetHeight) {
        Bitmap newBitmap = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // 计算缩放比例
            float scaleWidth = targetWidth / width;
            float scaleHeight = targetHeight / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    matrix, true);
            // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
            bitmap.recycle();
        }
        return newBitmap;
    }
}

package com.gzrijing.workassistant.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    /**
     * 存放拍照图片的uri地址
     */
    public static Uri imageUriFromCamera;


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
     * 获取指定图片并显示
     */
    public static void displayImage(Context context, String picUrl, ImageView imageView){
        File path = ImageUtils.getImagePath(context);
        if(path==null){
            return;
        }
        String url = path.getPath() + "/" + picUrl;
        String imageUrl = ImageDownloader.Scheme.FILE.wrap(url);
        imageLoader.displayImage(imageUrl, imageView, ImageOptHelper.getImgOptions());
    }

    /**
     * 显示获取照片不同方式对话框
     */
    public static void showImagePickDialog(final Activity activity) {
        String title = "选择获取图片方式";
        String[] items = new String[]{"拍照", "相册"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 打开相机拍照获取图片
     */
    public static void pickImageFromCamera(final Activity activity) {
        imageUriFromCamera = createImageUri(activity);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    /**
     * 打开本地相册选取图片
     */
    public static void pickImageFromAlbum(final Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
    }

    /**
     * 打开本地相册选取图片2
     */
    public static void pickImageFromAlbum2(final Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
    }

    /**
     * 创建一条图片uri,用于保存拍照后的照片
     */
    public static Uri createImageUri(Context context) {
        String name = "boreWbImg" + System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }

    /**
     * 删除一条图片
     */
    public static void deleteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }

    /**
     * 获取相册图片路径
     * @param uri
     * @return
     */
    public static String getPicPath(Context context, Uri uri) {
        ContentResolver mResolver = context.getContentResolver();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mResolver.query(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

}

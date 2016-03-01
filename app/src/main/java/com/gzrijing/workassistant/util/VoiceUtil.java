package com.gzrijing.workassistant.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * 声音工具
 */
public class VoiceUtil {

    public static File getVoicePath(Context context, String userNo, String orderId){
        // 未安装SD卡时不做保存
        String storageState = Environment.getExternalStorageState();
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showToast(context, "未检测到SD卡", Toast.LENGTH_SHORT);
            return null;
        }

        // 声音文件保存路径
        File storageDirectory = Environment.getExternalStorageDirectory();
        File path = new File(storageDirectory, "/GZRJWorkassistant/voice/"+userNo+"/"+orderId);
        // 声音路径不存在创建之
        if (!path.exists()) {
            path.mkdirs();
        }

        return path;
    }

}

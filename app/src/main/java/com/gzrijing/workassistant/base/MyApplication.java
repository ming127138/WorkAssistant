package com.gzrijing.workassistant.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

public class MyApplication extends LitePalApplication {
    private static Context context;
    private SQLiteDatabase db;
    private ImageLoaderConfiguration configuration;
    private PushManager pushManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if(db == null){
            db = Connector.getDatabase(); //创建数据表
        }

        if(configuration == null){
            //创建默认的ImageLoader配置参数
            configuration = ImageLoaderConfiguration
                    .createDefault(this);
        }
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        if(pushManager == null){
            pushManager = PushManager.getInstance();
        }
        pushManager.initialize(this);
        String clientid = pushManager.getClientid(this);
        Log.e("clientid", clientid);

    }

    public static Context getContext() {
        return context;
    }
}

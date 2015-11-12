package com.gzrijing.workassistant.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

public class MyApplication extends LitePalApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        SQLiteDatabase db = Connector.getDatabase(); //创建数据表
    }

    public static Context getContext() {
        return context;
    }
}

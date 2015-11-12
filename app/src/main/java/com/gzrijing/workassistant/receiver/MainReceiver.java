package com.gzrijing.workassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.service.GetLeaderBusinessService;

public class MainReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("")) {
            GetLeaderBusiness();
        }
    }

    private void GetLeaderBusiness() {
        Intent intent = new Intent(MyApplication.getContext(), GetLeaderBusinessService.class);
        MyApplication.getContext().startService(intent);
    }
}

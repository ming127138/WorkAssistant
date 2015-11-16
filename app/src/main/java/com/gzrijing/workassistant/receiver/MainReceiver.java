package com.gzrijing.workassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.service.GetLeaderBusinessService;
import com.igexin.sdk.PushConsts;

public class MainReceiver extends BroadcastReceiver {
    /**
     * 推送入口
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    if (data.equals("0")) {
                        GetLeaderBusiness();
                    }
                }
                break;
        }
    }

    private void GetLeaderBusiness() {
        Intent intent = new Intent(MyApplication.getContext(), GetLeaderBusinessService.class);
        MyApplication.getContext().startService(intent);
    }
}

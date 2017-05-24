package com.qdxy.app.lhjh.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionDaoJuList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionEquList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionJiaGongList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionJudgementList;
import com.qdxy.app.lhjh.activities.home.ActHome;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by KY on 2016/12/22.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        //        Debug.info(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Debug.info(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Debug.info(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Debug.info(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Debug.info(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            Debug.info(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Debug.info(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Debug.info(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Debug.info(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int type = -1;
        String aty = null;
        Log.v("result", extras);
        try {
            JSONObject extrasJson = new JSONObject(extras);
            if (!extrasJson.isNull("type")) {
                type = extrasJson.optInt("type");
            }
            if (!extrasJson.isNull("activity")) {
                aty = extrasJson.optString("activity");
            }

            Debug.info(TAG, "type=" + type);
        } catch (Exception e) {
            e.printStackTrace();
            Debug.info(TAG, "Unexpected: extras is not a valid json");
            return;
        }
        Intent intent = null;

        if (type != -1) {
            switch (type) {
                case 0:

                    intent = new Intent(context, ActExceptionJiaGongList.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    break;
                case 1:
                    intent = new Intent(context, ActExceptionEquList.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case 2:
                    intent = new Intent(context, ActExceptionDaoJuList.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case 3:
                    intent = new Intent(context, ActExceptionJudgementList.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case 4:
                    intent = new Intent(context, ActExceptionJudgementList.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                default:
                    intent = new Intent(context, ActHome.class);
                    //                intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } else {
            try {
                intent = new Intent(context, Class.forName("com.qdxy.app.lhjh.activities." + aty));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        context.startActivity(intent);

    }
}

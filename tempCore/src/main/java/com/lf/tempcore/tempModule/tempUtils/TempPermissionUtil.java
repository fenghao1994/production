package com.lf.tempcore.tempModule.tempUtils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.lf.tempcore.tempModule.tempDebuger.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longf on 2016/6/3.
 */
public class TempPermissionUtil {
    private static final String TAG = "TempPermissionUtil";
    private static final int REQUEST_CODE=600;
    /**
     * 请求高德地图相关权限
     *
     * @param cxt
     * @return 返回true 可直接使用权限
     */
    public static boolean requestNormalPermission(final Context cxt) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList =findDeniedPermissions(
                    (Activity) cxt,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA);

            if ( ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_WIFI_STATE)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)||
                    ((Activity) cxt).shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(cxt, "请允许以下权限，才能正常使用应用！", Toast.LENGTH_LONG).show();
                String[] arrayPer = new String[permissionList.size()];
                for (int i=0;i<permissionList.size();i++){
                    arrayPer[i]=permissionList.get(i);
                }
                if (arrayPer.length>0){
                    ((Activity) cxt).requestPermissions(arrayPer,REQUEST_CODE);
                }else{
                    return true;
                }

            }else{
                String[] arrayPer = new String[permissionList.size()];
                for (int i=0;i<permissionList.size();i++){
                    arrayPer[i]=permissionList.get(i);
                }
                if (arrayPer.length>0){
                    ((Activity) cxt).requestPermissions(arrayPer,REQUEST_CODE);
                }else{
                    return true;}
            }
            return false;
        }
        return true;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

  /*  private static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/

    /**
     * 检测照相机权限
     *
     * @param context
     * @return
     */
    public static boolean checkCameraPermission(Context context) {
        //检测sd卡读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                return false;
            }

        }
        return true;
    }

    /**
     * 请求照相机权限
     *
     * @param context
     */
    public static void requestCameraPermission(final Context context, final int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (!((Activity) context).shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    new AlertDialog.Builder(context)
                            .setMessage("请允许相机使用，才能正常使用当前功能！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                    return;
                }
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            }

        }
    }

   /* *//**检测储存卡读写权限
     * @param context
     * @return
     *//*
    public static boolean checkWriteAndReadPermission( Context context) {
        //检测sd卡读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

    *//*
    public static void requestWriteAndReadPermission(Context context,int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }*/

    /**
     * 请求打电话权限
     *
     * @param context
     * @param requestCode
     */
    public static void requestCallPhonePermission(Context context, int requestCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.CALL_PHONE}, requestCode);
            }

        }

    }

    /**
     * 请求储存卡读写权限
     *
     * @param context
     */
    public static void requestWriteAndReadPermissionGroup(final Context context, final int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!((Activity) context).shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !((Activity) context).shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(context)
                            .setMessage("您需要允许内存卡使用权限，才能正常使用当前功能！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                    return;
                }
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            }

        }

    }

//    private void checkStoragePremission(Context context){

    /* if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
         showMessageOKCancel("你需要同意使用sd卡权限，才能正常使用当前应用！",
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                         if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                             requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                                     0);
                         }});
                 }
     }*/
    public static boolean onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                Debug.info(TAG,"地图权限返回");
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Debug.info(TAG,"申请失败权限="+permissions[i]);
                        return false;
                    }
                }
                Debug.info(TAG,"申请权限成功");
                return true;
        }
        return false;
    }
}


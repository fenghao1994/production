package com.lf.tempcore.tempFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lf.tempcore.R;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempUtils.TempColoredSnackbar;
import com.lf.tempcore.tempViews.TempCustomProgressDialog;

/**
 * Created by longf on 2016/5/11.
 */
public abstract class TempFragment extends TempBaseFragment {

    private AlertDialog.Builder mCustomDialogBuilder;
//    private CompositeSubscription mCompositeSubscription;
//    public CompositeSubscription getCompositeSubscription() {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }
//
//        return this.mCompositeSubscription;
//    }
//
//
//    public void addSubscription(Subscription s) {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }
//
//        this.mCompositeSubscription.add(s);
//    }
public void superViewError(View parent,TempErrorCode code, String message){
    switch (code) {
        case ERROR_FAILED:
            showSnackBar(parent,message, 2, Snackbar.LENGTH_LONG);
            break;
        case TIME_OUT:
            showSnackBar(parent,getResources().getString(R.string.connect_timeout), 2, Snackbar.LENGTH_LONG);
            break;
        case ERROR_NET_DISCONTECTED:
            showSnackBar(parent,"网络连接已断开！", 2, Snackbar.LENGTH_LONG);
            break;
        case RUN_TIME_EXCEPTION:
            showSnackBar(parent,message, 2, Snackbar.LENGTH_LONG);
            break;
        case PARSE_ERROR:
            showSnackBar(parent,message, 2, Snackbar.LENGTH_LONG);
            break;
        default:
            showSnackBar(parent,getResources().getString(R.string.connect_error), 2, Snackbar.LENGTH_LONG);
            break;
    }
}
    /**
     * @param container The view to find a parent from.
     * @param message   - The text to show. Can be formatted text.
     * @param type      -  0:info(blue) 1:alert(red) 2:warning(yellow) 3:confirm(green)
     * @param duration  - How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
     */
    public void showSnackBar(View container, String message, int type, int duration) {
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT);
        switch (type) {
            case 0:
                TempColoredSnackbar.info(snackbar).show();
                break;
            case 1:
                TempColoredSnackbar.alert(snackbar).show();
                break;
            case 2:
                TempColoredSnackbar.warning(snackbar).show();
                break;
            case 3:
                TempColoredSnackbar.confirm(snackbar).show();
                break;
            default:
                TempColoredSnackbar.info(snackbar).show();
                break;
        }
    }

    /**
     * 等待加载对话框
     */
    private TempCustomProgressDialog mProgressDailog;

    protected void showConnectedFaildToast() {
        showToast("获取数据失败！");
    }

    protected void showToast(String msg) {
        if (getActivity() == null || msg == null) {
            return;
        }
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    protected void showConfirmationDialog(Context context,boolean touchOutSide, String title, String message, DialogInterface.OnClickListener posiListener, DialogInterface.OnClickListener nageListener){
        showTempDialog(context,touchOutSide,title,message,"确定",posiListener,"取消",nageListener);
    }
    protected void showMessageDialog(Context context,boolean touchOutSide,String title,String message,DialogInterface.OnClickListener posiListener){
        showTempDialog(context,touchOutSide,title,message,"确定",posiListener,"",null);
    }
    protected void showTempDialog(Context context, boolean touchOutSide, String title, String message, String posiButtonName, DialogInterface.OnClickListener posiListener, String nageButtonName, DialogInterface.OnClickListener nageListener){
        if (mCustomDialogBuilder==null){
            mCustomDialogBuilder =new AlertDialog.Builder(context,R.style.temp_dialog_theme);
        }

        mCustomDialogBuilder.setMessage(message);
            mCustomDialogBuilder.setTitle(TextUtils.isEmpty(title)?"":title);
        if (TextUtils.isEmpty(posiButtonName)){
            mCustomDialogBuilder.setPositiveButton("确定", posiListener);
        }else{
            mCustomDialogBuilder.setPositiveButton(posiButtonName, posiListener);
        }
        if (TextUtils.isEmpty(nageButtonName)){
            mCustomDialogBuilder.setNegativeButton("取消", nageListener);
        }else{
            mCustomDialogBuilder.setNegativeButton(nageButtonName, nageListener);
        }
        AlertDialog dialog =mCustomDialogBuilder.create();
        dialog .setCanceledOnTouchOutside(touchOutSide);
        dialog.show();
    }
    /**
     * 显示等待加载对话框
     * 默认不允许触摸隐藏
     */
    private void showProgressDialog() {
        if (mProgressDailog != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProgressDailog.create();
            }
            mProgressDailog.show();
        }
    }
    public void superViewProgress(){
        showProgressDialog(false);
    }
    public void superViewDismissProgress(){
        dismissProgressDialog();
    }
    public void superViewMessage(View parent,String message){
        showSnackBar(parent,message, 0, Snackbar.LENGTH_SHORT);
    }
    /**
     * 显示等待加载对话框
     *
     * @param shouldCanceledOnTouchOutside 允许触摸隐藏
     */
    protected void showProgressDialog(boolean shouldCanceledOnTouchOutside) {

        if (mProgressDailog != null) {
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        } else {
            mProgressDailog = new TempCustomProgressDialog(getActivity(), getResources().getString(R.string.loading));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProgressDailog.create();
            }
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        }
    }

    /**
     * 消失等待对话框
     */
    protected void dismissProgressDialog() {
        if (mProgressDailog != null) {
            mProgressDailog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (this.mCompositeSubscription != null) {
//            this.mCompositeSubscription.unsubscribe();
//        }
    }
}

package com.lf.tempcore.tempActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lf.tempcore.R;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempUtils.TempColoredSnackbar;
import com.lf.tempcore.tempViews.TempCustomProgressDialog;

import java.lang.ref.WeakReference;


/**
 * Author：longf on 2016/1/21 11:24
 *
 */
public  abstract class TempActivity extends TempBaseActivity {
    // 屏幕宽度
    private float Width;
    // 屏幕高度
    private float Height;
    private boolean keyboardAutoHide;
//    private CompositeSubscription mCompositeSubscription;
    private AlertDialog.Builder mCustomDialogBuilder;
    private ViewGroup mRootView;
//    public CompositeSubscription getCompositeSubscription() {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }
//
//        return this.mCompositeSubscription;
//    }
//
//    public void addSubscription(Subscription s) {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }
//
//        this.mCompositeSubscription.add(s);
//    }

    private WeakReference<TempActivity> mContext = new WeakReference<>(TempActivity.this);

    public void superViewProgress(){
        showProgressDialog(false);
    }
    public void superViewDismissProgress(){
        dismissProgressDialog();
    }
    public void superViewMessage(String message){
        showSnackBar(message, 0, Snackbar.LENGTH_SHORT);
    }
    public void superViewError(TempErrorCode code, String message){
        switch (code) {
            case ERROR_FAILED:
                showSnackBar(message, 2, Snackbar.LENGTH_LONG);
                break;
            case TIME_OUT:
                showSnackBar(getResources().getString(R.string.connect_timeout), 2, Snackbar.LENGTH_LONG);
                break;
            case ERROR_NET_DISCONTECTED:
                showSnackBar("网络连接已断开！", 2, Snackbar.LENGTH_LONG);
                break;
            case RUN_TIME_EXCEPTION:
                showSnackBar(message, 2, Snackbar.LENGTH_LONG);
                break;
            case PARSE_ERROR:
                showSnackBar(message, 2, Snackbar.LENGTH_LONG);
            default:
                showSnackBar(getResources().getString(R.string.connect_error), 2, Snackbar.LENGTH_LONG);
                break;
        }
    }
    public  void setScreenPORTRAIT(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /**
     * 等待加载对话框
     */
    private TempCustomProgressDialog mProgressDailog;
    public TempActivity getTempContext() {
        if (mContext.get() == null) {
            mContext = new WeakReference<>(TempActivity.this);
        }
        return mContext.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setScreenPORTRAIT();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Width = dm.widthPixels;
        Height = dm.heightPixels;
        Debug.info("Width="+Width+"||Height="+Height);
        super.onCreate(savedInstanceState);
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
//        returnBack();
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
    }

    public void performBackClicked(){
        onBackPressed();
    }
    /**
     * 显示等待加载对话框
     * 默认不允许触摸隐藏
     */
    private void showProgressDialog() {
        if (mProgressDailog != null) {
            mProgressDailog.show();
        }
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
            mProgressDailog = new TempCustomProgressDialog(this, getResources().getString(R.string.temp_loading));
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
//    <API> Class<API> createAction(Class<API> clazz){
//        return clazz;
//    }
//   protected   <API> API getAction(Class<API> clazz){
//
//       return RemoteApiFactory.createRemoteApi(clazz);
//   }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDailog!=null){
            mProgressDailog.dismiss();
            mProgressDailog=null;
        }
//        if (this.mCompositeSubscription != null) {
//            this.mCompositeSubscription.unsubscribe();
//        }
    }
    protected void showConnectedFaildToast(){
        showToast("连接失败,请稍后重试！");
    }
    protected void showToast(String msg){
            Toast.makeText( getApplicationContext(),msg, Toast.LENGTH_SHORT).show();

    }


    /**Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
     * 导航返回上一页
     */
    protected void initToolbar(Toolbar toolbarTop,String title) {

//        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        if (toolbarTop!=null){
            toolbarTop.setTitle("");
//            toolbarTop.setBackgroundColor(getResources().getColor(R.color.temp_colorPrimary));
            toolbarTop.setNavigationIcon(R.mipmap.top_bar_back_icon);
            TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
            if (mTitle!=null)
                mTitle.setText(TextUtils.isEmpty(title)?getResources().getString(R.string.app_name):title);

            setSupportActionBar(toolbarTop);
            toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

    }

    /**Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
     * 导航返回上一页
     */
    protected void initToolbar(String title) {

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        if (toolbarTop!=null){
            toolbarTop.setTitle("");
//            toolbarTop.setBackgroundColor(getResources().getColor(R.color.temp_colorPrimary));
            toolbarTop.setNavigationIcon(R.mipmap.top_bar_back_icon);
            TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
            if (mTitle!=null)
                mTitle.setText(TextUtils.isEmpty(title)?getResources().getString(R.string.app_name):title);

            setSupportActionBar(toolbarTop);
            toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

    }
    protected  void showLoginDialog(DialogInterface.OnClickListener posiListener){
        showConfirmationDialog(getTempContext(), false, "", "请先登录后，才能继续！", posiListener,null);
    }
    protected void showConfirmationDialog(Context context,boolean touchOutSide, String title, String message, DialogInterface.OnClickListener posiListener, DialogInterface.OnClickListener nageListener){
        showTempDialog(context,touchOutSide,title,message,"确定",posiListener,"取消",nageListener);
    }
    protected void showMessageDialog(Context context,boolean touchOutSide,String title,String message,DialogInterface.OnClickListener posiListener){
        showTempDialog(context,touchOutSide,title,message,"确定",posiListener,"",null);
    }
    /*protected void showTempDialog(Context context,boolean touchOutSide, String title, String message, String posiButtonName, DialogInterface.OnClickListener posiListener, String nageButtonName, DialogInterface.OnClickListener nageListener){
        if (mCustomDialogBuilder==null){
            mCustomDialogBuilder =new AlertDialog.Builder(context,R.style.temp_dialog_theme);
        }

        mCustomDialogBuilder.setMessage(message);
        if (!TextUtils.isEmpty(title)){
            mCustomDialogBuilder.setTitle(title);
        }
        if (!TextUtils.isEmpty(posiButtonName)){
            mCustomDialogBuilder.setPositiveButton("确定", posiListener);
        }
        if (!TextUtils.isEmpty(nageButtonName)){
            mCustomDialogBuilder.setNegativeButton("取消", nageListener);
        }
        AlertDialog dialog =mCustomDialogBuilder.create();
        dialog .setCanceledOnTouchOutside(touchOutSide);
        dialog.show();
    }*/
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
    public boolean isKeyboardAutoHide() {
        return keyboardAutoHide;
    }

    /**
     * @param message - The text to show. Can be formatted text.
     * @param type    -  0:info(blue) 1:alert(red) 2:warning(yellow) 3:confirm(green)
     * @param duration - How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
     *
     */
    public void showSnackBar(String message,int type ,int duration) {
        if (mRootView == null) {
            mRootView = (ViewGroup) ((Activity) TempActivity.this).findViewById(android.R.id.content);
        }
        showSnackBar(mRootView,message,type,duration);
    }
/**
 * @param container The view to find a parent from.
 * @param message - The text to show. Can be formatted text.
 * @param type    -  0:info(blue) 1:alert(red) 2:warning(yellow) 3:confirm(green)
 * @param duration - How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
 *
 */
    public void showSnackBar(View container,String message,int type ,int duration){
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT);
        switch (type){
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
    /**让键盘自动隐藏
     * @param keyboardAutoHide
     */
    public void setKeyboardAutoHide(boolean keyboardAutoHide) {
        this.keyboardAutoHide = keyboardAutoHide;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
//          Log.d("lf", "dianji");
        if (ev.getAction() == MotionEvent.ACTION_DOWN&&keyboardAutoHide) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public float getWidth() {
        return Width;
    }

    public void setWidth(float width) {
        Width = width;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

}

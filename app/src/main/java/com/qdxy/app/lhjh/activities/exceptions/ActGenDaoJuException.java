package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.selectors.ActSelector;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 创建刀具异常
 * Created by KY on 2016/11/9.
 */

public class ActGenDaoJuException extends TempActivity {
    private final String TAG ="ActGenDaoJuException";
    private int mDeviceId=-1,mDaoJuId=-1,exceptionId=-1,lineId=-1;
    private PreException preException;
    private ViewExceptionI viewExceptionI;

    @Bind(R.id.act_gen_daoju_equp_text)TextView act_gen_daoju_equp_text;
    @Bind(R.id.act_gen_daoju_daoju_text)TextView act_gen_daoju_daoju_text;
    @Bind(R.id.act_gen_daoju_desc_editText)EditText act_gen_daoju_desc_editText;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_gen_daoju_exception_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "刀具异常通知");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {
        lineId =getIntent().getIntExtra("lineId",-1);
        viewExceptionI =new ViewExceptionI() {
            @Override
            public void onJudgementDetailSucceed(RespExceptionJudgement data) {
            }

            @Override
            public void onDaojuHandeSucceed() {

            }

            @Override
            public void onOnProblemSucceed(String data) {
                Debug.info(TAG,"刀具异常提交成功");
                setResult(200);
                finish();
            }

            @Override
            public void onDaojuDetailSucceed(RespProblemDetail data) {

            }

            @Override
            public TempNetType checkNetWork() {
                return MyApplication.getInstance().getNetType();
            }

            @Override
            public void viewProgress() {
                superViewProgress();
            }

            @Override
            public void viewDismissProgress() {
                superViewDismissProgress();
            }

            @Override
            public void viewMsg(String message) {
                superViewMessage(message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        };
        preException = new PreException(viewExceptionI);
    }

    @Override
    protected void bindValues() {
        exceptionId =getIntent().getIntExtra("id",-1);
    }
        @OnClick({R.id.act_gen_daoju_equp_check_btn,R.id.act_gen_daoju_check_btn,R.id.act_gen_daoju_commit_button})
    @Override
    protected void OnViewClicked(View v) {
            switch (v.getId()){
                case R.id.act_gen_daoju_check_btn:
                    //选择刀具
                    if (exceptionId==-1){
                        showSnackBar("没有获取到异常数据！",2, Snackbar.LENGTH_LONG);
                        return;
                    }
                    if (mDeviceId==-1){
                        showSnackBar("请先选择设备！",2, Snackbar.LENGTH_LONG);
                        return;
                    }
                    Intent daojuIntent = new Intent(ActGenDaoJuException.this, ActSelector.class);
                    daojuIntent.putExtra("type",3);
                    daojuIntent.putExtra("deviceId",mDeviceId);
                    daojuIntent.putExtra("lineId",lineId);
                    startActivityForResult(daojuIntent,70);
                    break;
                case R.id.act_gen_daoju_equp_check_btn:
                    //选择设备
                    if (exceptionId==-1){
                        showSnackBar("没有获取到异常数据！",2, Snackbar.LENGTH_LONG);
                        return;
                    }
                    Intent equpIntent = new Intent(ActGenDaoJuException.this, ActSelector.class);
                    equpIntent.putExtra("type",1);
                    equpIntent.putExtra("lineId",lineId);
                    startActivityForResult(equpIntent,80);
                    break;
                case R.id.act_gen_daoju_commit_button:
                    //创建设备异常
                    if (exceptionId==-1){
                        showSnackBar("没有获取到异常数据！",2, Snackbar.LENGTH_LONG);
                    }  else if (mDeviceId==-1){
                        showSnackBar("请选择设备！",2, Snackbar.LENGTH_LONG);
                        return;
                    }else if (mDaoJuId==-1){
                        showSnackBar("请选择刀具！",2, Snackbar.LENGTH_LONG);
                        return;
                    }else{

                        preException.handProblem(exceptionId+"","43","",mDaoJuId+"",act_gen_daoju_equp_text.getText().toString().trim()+"",act_gen_daoju_desc_editText.getText().toString().trim());
                    }
                    break;

            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==200&&data!=null){
            if (requestCode==80){
                mDeviceId=data.getIntExtra("id",-1);
                Debug.info(TAG,"设备选择返回mDeviceId="+mDeviceId);
                String name=data.getStringExtra("name");
                act_gen_daoju_equp_text.setText(name);
            }else if(requestCode==70){
                mDaoJuId=data.getIntExtra("id",-1);
                String name=data.getStringExtra("deviceToolTypeName");
                Debug.info(TAG,"刀具选择返回id="+mDaoJuId);
                Debug.info(TAG,"刀具选择返回name="+name);
                act_gen_daoju_daoju_text.setText(name);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

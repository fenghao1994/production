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
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.selectors.ActSelector;

import butterknife.Bind;
import butterknife.OnClick;

/**创建设备异常
 * Created by KY on 2016/11/9.
 */

public class ActGenEquException extends TempActivity{
    @Bind(R.id.act_gen_equ_device_text)    TextView act_gen_equ_device_text;
    @Bind(R.id.act_gen_equ_desc_editText)    EditText act_gen_equ_desc_editText;
    private int mDeviceId=-1,exceptionId=-1,lineId=-1;
    private PreException preException;
    private ViewExceptionI viewExceptionI;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_gen_equ_exception_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "设备异常通知");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {
        lineId=getIntent().getIntExtra("lineId",-1);
        viewExceptionI =new ViewExceptionI() {
            @Override
            public void onJudgementDetailSucceed(RespExceptionJudgement data) {
            }

            @Override
            public void onOnProblemSucceed(String data) {
                setResult(200);
                finish();
            }

            @Override
            public void onDaojuDetailSucceed(RespProblemDetail data) {

            }

            @Override
            public void onDaojuHandeSucceed() {

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
    @OnClick({R.id.act_gen_equ_selector_btn,R.id.act_gen_equ_commit_text})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_gen_equ_selector_btn:
                Intent intent = new Intent(ActGenEquException.this, ActSelector.class);
                intent.putExtra("type",1);
                intent.putExtra("lineId",lineId);
                startActivityForResult(intent,80);
                break;
            case R.id.act_gen_equ_commit_text:
                if (mDeviceId==-1){
                    showSnackBar("请选择设备！",2, Snackbar.LENGTH_LONG);

                }else if(exceptionId==-1){
                    showSnackBar("没有获取到异常数据！",2, Snackbar.LENGTH_LONG);
                }else{
                    //创建设备异常
                    preException.handProblem(exceptionId+"","42","","",act_gen_equ_device_text.getText().toString().trim(),act_gen_equ_desc_editText.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode==80&&resultCode==200&&data!=null){
                mDeviceId=data.getIntExtra("id",-1);
                String name=data.getStringExtra("name");
                act_gen_equ_device_text.setText(name);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

package com.qdxy.app.lhjh.activities.login;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by KY on 2016/11/4.
 */

public class ActLogin extends TempActivity implements ViewLogin {
    @Bind(R.id.act_login_userName)
    EditText actLoginUserName;
    @Bind(R.id.act_login_password)
    EditText actLoginPassword;
    @Bind(R.id.act_login_btn)
    Button actLoginBtn;
    private PreLogin mPreLogin;
    @Override
    protected void initContentView(Bundle savedInstanceState) {

        setContentView(R.layout.act_login_layout);
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setListeners() {
        setKeyboardAutoHide(true);

    }

    @Override
    protected void bindValues() {
//        JPushInterface.
        TempLoginConfig.clearJPushTable();//清除极光推送注册alias
//        TempLoginConfig.sf_getUserName()
        actLoginUserName.setText(TempLoginConfig.sf_getUserPhone());
        /*if (TempLoginConfig.sf_getLoginState()){
            superViewProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    superViewDismissProgress();
                    startActivity(new Intent(ActLogin.this, ActHome.class));
                    finish();
                }
            },1000);
        }else{*/
            mPreLogin = new PreLogin(this);
//        }
    }

    @OnClick({R.id.act_login_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.act_login_btn:
                mPreLogin.requestLogin(actLoginUserName.getText().toString(), actLoginPassword.getText().toString());
        }
    }

    @Override
    public void onLoginSucceed(RespLogin data) {
//        Intent intent = new Intent(ActLogin.this, ActHome.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        setResult(200);
        finish();
    }

    @Override
    public void viewPswInvalid(String message) {
        showSnackBar(message,1, Snackbar.LENGTH_SHORT);
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
        TempLoginConfig.sf_saveLoginState(false);
       superViewError( code,  message);

    }
    /*private void showSnackbar(){
    Snackbar.make()
}*/
//}
}

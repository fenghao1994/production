package com.qdxy.app.lhjh.activities.login;

import android.text.TextUtils;

import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPresenterDefault;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.ResponseLoginInfo;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.config.AppConfig;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KY on 2016/11/18.
 */

public class PreLogin extends TempPresenterDefault<ViewLogin>{
    private final String TAG = "PreLogin";
//    private ViewLogin mView;

    public PreLogin(ViewLogin viewLogin) {
        super(viewLogin);
    }


    /*public PreLogin(ViewLogin View) {
        this.mView = View;
    }*/

    public void requestLogin(final String userName, String Psw) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(Psw)) {
            if (getView() != null) {
                getView().viewPswInvalid("用户名或密码不能为空");
            }
            return;
        }
        if (AppConfig.APP_DEBUG) {
            if (getView() != null) {
                if (getView() != null) {
                    RespLogin loginData = new RespLogin();
                    RespLogin.DataBean dataBean = new RespLogin.DataBean();
                    dataBean.setToken("123");
                    dataBean.setId(1);
                    dataBean.setUserName("LongF");
                    loginData.setData(dataBean);
                    ResponseLoginInfo info = new ResponseLoginInfo();
                    ResponseLoginInfo.ResultEntity entity = new ResponseLoginInfo.ResultEntity();
                    entity.setMuseId("1");
                    entity.setMuseUserName("test");
                    entity.setMusePhone(userName);
                    entity.setMuseOnlineTag("123");
                    entity.setWorksSatus(1);
//                                TempLoginConfig.sf_saveUserName();
                    TempLoginConfig.sf_saveLoginInfo(info);
                    TempLoginConfig.sf_saveLoginState(true);
                    getView().onLoginSucceed(loginData);
                }
            }
            return;
        }
        Debug.info("发送请求");
        if (getView().checkNetWork() == TempNetType.NET_DISABLED) {
            getView().viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        } else {
            if (getView() != null) {
                getView().viewProgress();
            }
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).userLogin(userName, Psw), new Callback<RespLogin>() {
                @Override
                public void onResponse(Call<RespLogin> call, Response<RespLogin> response) {

                    if (getView() != null) {
                        getView().viewDismissProgress();
                    }
                    RespLogin data = response.body();
                    if (data == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else if (data.isSuccess() && getView() != null){

                            Debug.info(response.body().toString());
                            ResponseLoginInfo info = new ResponseLoginInfo();
                            ResponseLoginInfo.ResultEntity entity = new ResponseLoginInfo.ResultEntity();
                            entity.setMuseId(data.getData().getId() + "");
                            entity.setMuseUserName(data.getData().getUserName());
                            entity.setMuseOnlineTag(data.getData().getToken());
                            entity.setMusePhone(userName);
                            entity.setWorksSatus(data.getData().getWorkStatus());
                            info.setResult(entity);
//                                TempLoginConfig.sf_saveUserName();
                            TempLoginConfig.sf_saveLoginInfo(info);
                        TempLoginConfig.sf_saveLoginState(true);

                            getView().onLoginSucceed(data);

                    }else{
                        TempLoginConfig.sf_saveLoginState(false);
                        getView().viewMsg("登录失败！");
                    }
                }

                @Override
                public void onFailure(Call<RespLogin> call, Throwable t) {
                    if (getView() != null) {
                        getView().viewDismissProgress();
                        if (t instanceof SocketTimeoutException) {
                            getView().viewError(TempErrorCode.TIME_OUT, "");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED, "");
                        }


                    }
                    t.printStackTrace();

                }
            });
        }
//                startActivity(new Intent(ActLogin.this,ActHome.class));
    }


}
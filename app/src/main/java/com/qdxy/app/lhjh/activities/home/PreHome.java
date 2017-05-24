package com.qdxy.app.lhjh.activities.home;

import com.google.gson.JsonSyntaxException;
import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPresenterDefault;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespPermission;
import com.qdxy.app.lhjh.config.AppConfig;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KY on 2016/11/21.
 */

public class PreHome extends TempPresenterDefault<ViewHome>{

    public PreHome(ViewHome viewHome) {
        super(viewHome);
    }

    /*
        public PreHome(ViewHome View) {
            this.mView = View;
        }*/
    public void requestPermission(){
        if (getView() == null) {
            Debug.info("view is null");
        }else if(getView().checkNetWork() == TempNetType.NET_DISABLED){
            getView().viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestPermission(), new Callback<RespPermission>() {
                @Override
                public void onResponse(Call<RespPermission> call, Response<RespPermission> response) {
                    getView().viewDismissProgress();
                    RespPermission data = response.body();
                    if (data == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  if (data.isSuccess() ) {
                            TempLoginConfig.sf_savePermission(data.getData());
                        Debug.info(response.body().toString());
//                        getView().viewMenuSucceed(data);
                        Debug.info("权限数据返回成功");

                    }else{
                        getView().viewMsg("失败啦！");
                    }

                }

                @Override
                public void onFailure(Call<RespPermission> call, Throwable t) {
                        getView().viewDismissProgress();
                    if (t instanceof IllegalStateException||t instanceof JsonSyntaxException){
                        getView().viewError(TempErrorCode.PARSE_ERROR,"数据解析出错");
                    }else if (t instanceof SocketTimeoutException) {
                            getView().viewError(TempErrorCode.TIME_OUT, "");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED, "");
                        }


                    t.printStackTrace();

                }
            });
        }
    }
    public void requestHomeMenu(String userId, String token) {
        if (getView() == null) {
            Debug.info("view is null");
        } else if (AppConfig.APP_DEBUG) {
            RespHomeMenuGroup group = new RespHomeMenuGroup();
            RespMenuChild child;
            List<RespMenuChild> listChild;
            RespHomeMenuGroup.DataBean bean;
            List<RespHomeMenuGroup.DataBean> groupList;

            //菜单数据1
            groupList = new ArrayList<>();

            bean = new RespHomeMenuGroup.DataBean();
            bean.setTitle("生产加工");

            listChild = new ArrayList<>();

            child = new RespMenuChild();
            child.setActivity("ActProduction");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("产品加工");
            listChild.add(child);

            child = new RespMenuChild();
            child.setActivity("");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("辅料领用");
            listChild.add(child);

            bean.setChildren(listChild);
            groupList.add(bean);

            //菜单数据2
//            groupList = new ArrayList<>();

            bean = new RespHomeMenuGroup.DataBean();
            bean.setTitle("异常处理");

            listChild = new ArrayList<>();

            child = new RespMenuChild();
            child.setActivity("exceptions.ActExceptionJudgementList");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("异常判断");
            listChild.add(child);

            child = new RespMenuChild();
            child.setActivity("exceptions.ActExceptionJiaGongList");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("加工异常");
            listChild.add(child);

            child = new RespMenuChild();
            child.setActivity("exceptions.ActExceptionEquList");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("设备异常");
            listChild.add(child);

            child = new RespMenuChild();
            child.setActivity("exceptions.ActExceptionDaoJuList");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("刀具异常");
            listChild.add(child);

            bean.setChildren(listChild);
            groupList.add(bean);

            bean = new RespHomeMenuGroup.DataBean();
            bean.setTitle("生产管理");

            listChild = new ArrayList<>();

            child = new RespMenuChild();
            child.setActivity("management.ActManagePiCi");
            child.setIcon("http://img0.imgtn.bdimg.com/it/u=1126541908,2603454962&fm=21&gp=0.jpg");
            child.setTitle("批次管理");
            listChild.add(child);

            bean.setChildren(listChild);
            groupList.add(bean);

            group.setData(groupList);//添加完数据
                getView().viewMenuSucceed(group);

        } else if (getView().checkNetWork() == TempNetType.NET_DISABLED) {
            getView().viewDismissProgress();
            getView().viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        } else {
            Debug.info("发送请求");
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestHomeMenu(), new Callback<RespHomeMenuGroup>() {
                @Override
                public void onResponse(Call<RespHomeMenuGroup> call, Response<RespHomeMenuGroup> response) {

                        getView().viewDismissProgress();
                    RespHomeMenuGroup data = response.body();
                    if (data == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  if (data.isSuccess() ) {

                            Debug.info(response.body().toString());
                            getView().viewMenuSucceed(data);

                    }else{
                        getView().viewMsg("失败啦！");
                    }
                }

                @Override
                public void onFailure(Call<RespHomeMenuGroup> call, Throwable t) {
                        getView().viewDismissProgress();
                        if (t instanceof SocketTimeoutException) {
                            getView().viewError(TempErrorCode.TIME_OUT, "");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED, "");
                        }


                    t.printStackTrace();

                }
            });
        }
//                startActivity(new Intent(ActLogin.this,ActHome.class));
    }
    /**
     * 获取未装箱的产品列表
     *
     */
    public void userLogout(String id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).userLogout(id), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onLogoutSucceed();
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
                            getView().onLogoutSucceed();
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 用户上下班
     *
     */
    public void requestWorkStatus(final String workStatus) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestWorkStatus(workStatus), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            if (workStatus.equals("0")){
                                TempLoginConfig.sf_saveWorkStatus(0);
                                getView().viewMsg("上班成功！");
                                getView().onWorkSucceed();
                            }else{
                                TempLoginConfig.sf_saveWorkStatus(1);
                                getView().viewMsg("下班成功！");
                                getView().onWorkFailed();
                            }
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
}

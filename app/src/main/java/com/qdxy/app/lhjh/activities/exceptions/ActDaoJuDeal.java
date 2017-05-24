package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.selectors.ActSelector;
import com.qdxy.app.lhjh.views.TempMyListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**刀具异常诊断
 * Created by KY on 2016/11/10.
 */

public class ActDaoJuDeal extends TempActivity {
    private final  String TAG="ActDaoJuDeal";
    private PreException preException;
    private int id;
    @Bind(R.id.act_exception_index0_text)TextView index0_text;
    @Bind(R.id.act_exception_index1_text)TextView index1_text;
    @Bind(R.id.act_exception_index2_text)TextView index2_text;
//    @Bind(R.id.act_exception_index3_text)TextView index3_text;
    @Bind(R.id.act_exception_index4_text)TextView index4_text;
    @Bind(R.id.act_exception_index5_text)TextView index5_text;
    @Bind(R.id.act_exception_index6_text)TextView index6_text;
    @Bind(R.id.act_exception_daoju_remark_text)EditText act_exception_daoju_remark_text;//异常处理备注
    @Bind(R.id.act_exception_daoju_zerenren_content)TextView act_exception_daoju_zerenren_content;
    @Bind(R.id.act_daoju_deal_listView)TempMyListView mlistView;
    @Bind(R.id.act_daoju_deal_result_frame)LinearLayout act_daoju_deal_result_frame;
    @Bind(R.id.act_exception_daoju_deal_radiogroup)RadioGroup act_exception_daoju_deal_radiogroup;
    @Bind(R.id.act_exception_daoju_deal_radioBtn0)RadioButton act_exception_daoju_deal_radioBtn0;
    @Bind(R.id.act_exception_daoju_deal_radioBtn1)RadioButton act_exception_daoju_deal_radioBtn1;
    private AdapterExceptionDeal adapter;
    private boolean isJudge;//是否已处理当前异常
    private int handType,ResponsiblePersonId=-1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_daoju_deal_layout);
    }

    @Override
    protected void findViews() {
        isJudge=getIntent().getBooleanExtra("isJudge",false);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "刀具异常处理");
        setKeyboardAutoHide(true);
        preException = new PreException(new ViewExceptionI() {
            @Override
            public void onJudgementDetailSucceed(RespExceptionJudgement data) {

            }

            @Override
            public void onOnProblemSucceed(String data) {

            }

            @Override
            public void onDaojuHandeSucceed() {
                showToast("提交成功");
                EventBus.getDefault().post("40");
                finish();
            }

            @Override
            public void onDaojuDetailSucceed(RespProblemDetail data) {
                index0_text.setText(data.getData().getDeviceToolName());//刀具名称
                index1_text.setText(data.getData().getDeviceCode());//设备编号
                index2_text.setText(data.getData().getCreationTime());//申请时间
//                index3_text.setText(data.getData().get());//刀具名称
                index6_text.setText(data.getData().getProductionLineName());//生产线
                index4_text.setText(data.getData().getProcedureName());//工序
                index5_text.setText(data.getData().getDescription());//异常描述
                adapter.upDateReflash(data.getData().getHandleResultList());
                if (isJudge){
                    //已处理该异常
                    act_daoju_deal_result_frame.setVisibility(View.GONE);
                }
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
                superViewError(code,message);
            }
        });
    }

    @Override
    protected void setListeners() {
        act_exception_daoju_deal_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (act_exception_daoju_deal_radioBtn0.getId()==checkedId){
                    Debug.info(TAG,"选中的是正常");
                    handType=30;
                }else{
                    Debug.info(TAG,"选中的是更换");
                    handType=31;
                }
            }
        });
        adapter = new AdapterExceptionDeal(new ArrayList<RespProblemDetail.DataBean.HandleResultListBean>(),this,R.layout.item_exception_deal_layout);
        mlistView.setAdapter(adapter);
    }
    @Override
    protected void bindValues() {
        id = getIntent().getIntExtra("id",-1);
        if (id==-1){
            showSnackBar("没有获取到异常数据id",2, Snackbar.LENGTH_LONG);
            return;
        }
        preException.deviceToolProblemDetail(id);
    }
    @OnClick({R.id.act_exception_daoju_zerenren_frame,R.id.act_exception_daoju_commit_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_exception_daoju_zerenren_frame:
                //选择人员
                Intent equpIntent = new Intent(ActDaoJuDeal.this, ActSelector.class);
                equpIntent.putExtra("type",0);
                startActivityForResult(equpIntent,80);
                break;
            case R.id.act_exception_daoju_commit_btn:
                //提交异常处理
                    if (handType==0){
                        showSnackBar("请在异常诊断中选择一项",2,Snackbar.LENGTH_LONG);
                    }else {
                        preException.deviceToolProblemHandleSubmit(id,handType,ResponsiblePersonId,act_exception_daoju_remark_text.getText().toString().trim());
                }
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==200&&data!=null){
            ResponsiblePersonId=data.getIntExtra("id",-1);
//                Debug.info(TAG,"设备选择返回mDeviceId="+mDeviceId);
                String name=data.getStringExtra("name");
            act_exception_daoju_zerenren_content.setText(name);
//                act_gen_daoju_equp_text.setText(name);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

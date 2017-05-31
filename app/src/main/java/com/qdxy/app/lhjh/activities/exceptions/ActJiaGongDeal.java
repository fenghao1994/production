package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 工件异常诊断
 * Created by KY on 2016/11/10.
 */

public class ActJiaGongDeal extends TempActivity {
    private final String TAG = "ActJiaGongDeal";
    @Bind(R.id.act_prd_check_checkbox6)
    AppCompatRadioButton act_prd_check_checkbox6;
    private PreException preException;
    @Bind(R.id.act_equ_judgement_listView)
    TempMyListView mMyListView;
    @Bind(R.id.act_exception_index0_text)
    TextView index0_text;
    @Bind(R.id.act_exception_index1_text)
    TextView index1_text;
    @Bind(R.id.act_exception_index2_text)
    TextView index2_text;
    @Bind(R.id.act_exception_index3_text)
    TextView index3_text;
    @Bind(R.id.act_exception_index4_text)
    TextView index4_text;
    @Bind(R.id.act_exception_deal_zerenren_text)
    TextView exception_deal_zerenren_text;
    @Bind(R.id.act_prd_check_checkbox0)
    AppCompatRadioButton act_prd_check_checkbox0;
    @Bind(R.id.act_prd_check_checkbox1)
    AppCompatRadioButton act_prd_check_checkbox1;
    @Bind(R.id.act_prd_check_checkbox2)
    AppCompatRadioButton act_prd_check_checkbox2;
    @Bind(R.id.act_prd_check_checkbox3)
    AppCompatRadioButton act_prd_check_checkbox3;
    @Bind(R.id.act_prd_check_checkbox4)
    AppCompatRadioButton act_prd_check_checkbox4;
    @Bind(R.id.act_prd_check_checkbox5)
    AppCompatRadioButton act_prd_check_checkbox5;
    @Bind(R.id.act_exception_deal_result_frame)
    LinearLayout deal_result_frame;
    @Bind(R.id.act_exception_deal_remark)
    EditText exception_deal_remark;//备注
    private AdapterExceptionDeal adapter;
    private int id;//异常id
    private int handType, ResponsiblePersonId = -1;
    private boolean isJudge;//是否已处理当前异常

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_gongjian_deal_layout);
    }

    @Override
    protected void findViews() {
        isJudge = getIntent().getBooleanExtra("isJudge", false);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "工件异常处理");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {


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
                index0_text.setText(data.getData().getProductCode());//工件编号
                index1_text.setText(data.getData().getCreationTime());//时间
                index2_text.setText(data.getData().getProductionLineName());//生产线
                index3_text.setText(data.getData().getProcedureName());//工序
                index4_text.setText(data.getData().getDescription());//描述
                adapter.upDateReflash(data.getData().getHandleResultList());
                if (isJudge) {
                    //已处理该异常
                    deal_result_frame.setVisibility(View.GONE);
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
                superViewError(code, message);
            }
        });
    }

    private void resetCheckBox() {
        act_prd_check_checkbox0.setChecked(false);
        act_prd_check_checkbox1.setChecked(false);
        act_prd_check_checkbox2.setChecked(false);
        act_prd_check_checkbox3.setChecked(false);
        act_prd_check_checkbox4.setChecked(false);
        act_prd_check_checkbox5.setChecked(false);
        act_prd_check_checkbox6.setChecked(false);
    }

    @Override
    protected void bindValues() {
        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            showSnackBar("没有获取到异常数据id", 2, Snackbar.LENGTH_LONG);
            return;
        }
        /*exception_deal_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (exception_deal_radiobtn0.getId()==checkedId){
                    Debug.info(TAG,"选中的是正常");
                    handType=20;
                }else if (exception_deal_radiobtn1.getId()==checkedId){
                    Debug.info(TAG,"选中的是无法维修");
                    handType=22;
                }else{
                    Debug.info(TAG,"选中的是维修完成");
                    handType=21;
                }
            }
        });*/
        act_prd_check_checkbox0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox0.setChecked(true);
                handType = 10;
                Debug.info(TAG, "触发点击事件正常" + handType);
            }
        });
        act_prd_check_checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox1.setChecked(true);
                handType = 11;
                Debug.info(TAG, "触发点击事件工废" + handType);
            }
        });
        act_prd_check_checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox2.setChecked(true);
                handType = 13;
                Debug.info(TAG, "触发点击事件料废" + handType);
            }
        });
        act_prd_check_checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox3.setChecked(true);
                handType = 12;
                Debug.info(TAG, "触发点击事件工二级" + handType);
            }
        });
        act_prd_check_checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox4.setChecked(true);
                handType = 14;
                Debug.info(TAG, "触发点击事件料二级" + handType);
            }
        });
        act_prd_check_checkbox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox5.setChecked(true);
                handType = 15;
                Debug.info(TAG, "触发点击事件返工" + handType);
            }
        });
        act_prd_check_checkbox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox6.setChecked(true);
                handType = 16;
            }
        });
        adapter = new AdapterExceptionDeal(new ArrayList<RespProblemDetail.DataBean.HandleResultListBean>(), this, R.layout.item_exception_deal_layout);
        mMyListView.setAdapter(adapter);
        preException.machiningPartProblemDetail(id);
    }

    @OnClick({R.id.act_exception_deal_zerenren_frame, R.id.act_exception_deal_commit})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.act_exception_deal_zerenren_frame:
                //选择人员
                Intent equpIntent = new Intent(ActJiaGongDeal.this, ActSelector.class);
                equpIntent.putExtra("type", 0);
                startActivityForResult(equpIntent, 80);
                break;
            case R.id.act_exception_deal_commit:
                //提交异常处理
                if (handType == 0) {
                    showSnackBar("请在异常诊断中选择一项", 2, Snackbar.LENGTH_LONG);
                } else {
                    preException.machiningPartProblemHandleSubmit(id, handType, ResponsiblePersonId, exception_deal_remark.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200 && data != null) {
            ResponsiblePersonId = data.getIntExtra("id", -1);
//                Debug.info(TAG,"设备选择返回mDeviceId="+mDeviceId);
            String name = data.getStringExtra("name");
            exception_deal_zerenren_text.setText(name);
//                act_gen_daoju_equp_text.setText(name);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.qdxy.app.lhjh.activities.qualityCheck;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by mac on 2017/1/16.
 */

public class ActQualityCheckDeal extends TempActivity {
    private final String TAG = "ActQualityCheckDeal";
    @Bind(R.id.act_exception_index0_text)    TextView act_exception_index0_text;
    @Bind(R.id.act_exception_index1_text)    TextView act_exception_index1_text;
    @Bind(R.id.act_exception_index2_text)    TextView act_exception_index2_text;
    @Bind(R.id.act_exception_index3_text)    TextView act_exception_index3_text;
    @Bind(R.id.act_exception_index4_text)    TextView act_exception_index4_text;
    @Bind(R.id.act_quality_check_deal_result_textView)    TextView act_quality_check_deal_result_textView;
    @Bind(R.id.act_quality_check_deal_info_textView)    TextView act_quality_check_deal_info_textView;
    @Bind(R.id.act_quality_check_result_text0)    TextView act_quality_check_result_text0;
    @Bind(R.id.act_quality_check_result_text1)    TextView act_quality_check_result_text1;
    @Bind(R.id.act_quality_check_result_text2)    TextView act_quality_check_result_text2;
    @Bind(R.id.act_quality_check_result_text3)    TextView act_quality_check_result_text3;
        @Bind(R.id.act_prd_check_radioGroup)      RadioGroup act_prd_check_radioGroup;
    @Bind(R.id.act_prd_check_checkbox0)    RadioButton act_prd_check_checkbox0;
    @Bind(R.id.act_prd_check_checkbox1)    RadioButton act_prd_check_checkbox1;
    @Bind(R.id.act_quality_check_check_frame) View act_quality_check_check_frame;
    @Bind(R.id.act_quality_check_result_frame) View act_quality_check_result_frame;
    @Bind(R.id.act_exception_deal_remark)    EditText act_exception_deal_remark;
    @Bind(R.id.act_exception_deal_commit)    AppCompatButton act_exception_deal_commit;
    private boolean isJudge;//是否已处理当前异常
    private int id;
    private int procedureId;
    private PreQualityCheck preQualityCheck;
    private boolean checkResult;
    private int mType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_quality_check_deal_layout);
    }

    @Override
    protected void findViews() {
        isJudge = getIntent().getBooleanExtra("isJudge", false);
        mType = getIntent().getIntExtra("type",-1);
        act_quality_check_deal_result_textView.setText(mType==0?"检测结果":"维修结果");
        act_quality_check_deal_info_textView.setText(mType==0?"送检信息":"维修信息");
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, mType==0?"质量检测详情":"工件维修详情");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {
        act_exception_deal_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType!=-1){

                    preQualityCheck.handleQualityCheck(id,procedureId,checkResult,act_exception_deal_remark.getText().toString().trim(),false);
                }else{
                    showSnackBar("数据有误，无法提交！",2,Snackbar.LENGTH_LONG);
                }
            }
        });
        act_prd_check_checkbox0.setText(mType==0?"合格":"维修完成");
        act_prd_check_checkbox1.setText(mType==0?"不合格":"维修失败");
        act_prd_check_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (act_prd_check_checkbox0.getId()==checkedId){
                    Debug.info(TAG,"选中的是正常");
                    checkResult=true;
//                    handType=30;
                }else{
                    Debug.info(TAG,"选中的是更换");
                    checkResult=false;
//                    handType=31;
                }
            }
        });

        act_prd_check_checkbox0.setChecked(true);
        preQualityCheck = new PreQualityCheck(new ViewQualityCheck() {

            @Override
            public void onHandleResultSucceed() {
                EventBus.getDefault().post("41");
                finish();
            }

            @Override
            public void onDetail(RespQualityDetail data) {
                act_exception_index0_text.setText(data.getData().getProductCode());
                act_exception_index1_text.setText(data.getData().getCreationTime());
                act_exception_index2_text.setText(data.getData().getProductLineName());
                act_exception_index3_text.setText(data.getData().getProcedureName());
                act_exception_index4_text.setText(data.getData().getCreationUserName());
                if (data.getData().isIsResult()){
                    //已經處理
                    act_quality_check_result_frame.setVisibility(View.VISIBLE);
                    act_quality_check_result_text0.setText(mType==0?(data.getData().isResult()?"合格":"不合格"):(data.getData().isResult()?"维修完成":"维修失败"));
                    act_quality_check_result_text1.setText(data.getData().getResultTime());
                    act_quality_check_result_text2.setText(data.getData().getResultUserName());
                    act_quality_check_result_text3.setText(data.getData().getRemark());

                }else{
                    //未处理
                    act_quality_check_check_frame.setVisibility(View.VISIBLE);


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

    @Override
    protected void bindValues() {
        id = getIntent().getIntExtra("id", -1);
        procedureId = getIntent().getIntExtra("procedureId", -1);

        if (id == -1||procedureId==-1||mType==-1) {
            showSnackBar("没有获取到送检数据id", 2, Snackbar.LENGTH_LONG);
            return;
        }
        preQualityCheck.qualityCheckDetail(id,procedureId,mType);
        // TODO: 2017/1/16 获取送检数据详情
    }

    @Override
    protected void OnViewClicked(View v) {

    }
}

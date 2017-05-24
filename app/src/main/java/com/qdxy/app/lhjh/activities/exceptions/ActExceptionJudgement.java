package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * 异常判断
 * Created by KY on 2016/11/8.
 */

public class ActExceptionJudgement extends TempActivity {
    private final String TAG="ActExceptionJudgement";
    @Bind(R.id.act_exception_judgement_time)    TextView act_exception_judgement_time;
    @Bind(R.id.act_exception_judgement_name)    TextView act_exception_judgement_name;
    @Bind(R.id.act_exception_judgement_organization)    TextView act_exception_judgement_organization;
    @Bind(R.id.act_exception_judgement_procedureName)    TextView act_exception_judgement_procedureName;
    @Bind(R.id.act_deal_exception_judgement_result_text)    TextView act_deal_exception_judgement_result_text;
    @Bind(R.id.act_deal_exception_judgement_desc_text)    TextView act_deal_exception_judgement_desc_text;
    @Bind(R.id.act_deal_exception_gongjian_btn)    LinearLayout act_deal_exception_gongjian_btn;
    @Bind(R.id.act_deal_exception_daoju_btn)    LinearLayout act_deal_exception_daoju_btn;
    @Bind(R.id.act_deal_exception_shebei_btn)    LinearLayout act_deal_exception_shebei_btn;
    @Bind(R.id.act_deal_exception_normal_btn)    LinearLayout act_deal_exception_normal_btn;
    @Bind(R.id.act_deal_exception_judgement_result_frame)    LinearLayout act_deal_exception_judgement_result_frame;
    @Bind(R.id.act_deal_exception_judgement_desc_frame)    LinearLayout act_deal_exception_judgement_desc_frame;
    @Bind(R.id.act_deal_exception_judgement_frame)    LinearLayout act_deal_exception_judgement_frame;
    private PreException preException;
    private ViewExceptionI viewExceptionI;
    private int exceptionId,lineId;//异常判断id
    private boolean isJudge;
//    private String mProcedureName;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_judgement_layout);
    }

    @Override
    protected void findViews() {
        isJudge=getIntent().getBooleanExtra("isJudge",false);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "异常判断");
        act_deal_exception_gongjian_btn.setClickable(false);
        act_deal_exception_daoju_btn.setClickable(false);
        act_deal_exception_shebei_btn.setClickable(false);
        act_deal_exception_normal_btn.setClickable(false);
        act_deal_exception_judgement_frame.setVisibility(isJudge?View.GONE:View.VISIBLE);
        act_deal_exception_judgement_result_frame.setVisibility(isJudge?View.VISIBLE:View.GONE);
        act_deal_exception_judgement_desc_frame.setVisibility(isJudge?View.VISIBLE:View.GONE);
    }

    @Override
    protected void setListeners() {
        viewExceptionI =new ViewExceptionI() {
            @Override
            public void onJudgementDetailSucceed(RespExceptionJudgement data) {
                lineId = data.getData().getProductionLineId();
                act_deal_exception_gongjian_btn.setClickable(true);
                act_deal_exception_daoju_btn.setClickable(true);
                act_deal_exception_shebei_btn.setClickable(true);
                act_deal_exception_normal_btn.setClickable(true);
                act_exception_judgement_procedureName.setText(getIntent().getStringExtra("procedureName"));
                act_exception_judgement_time.setText(data.getData().getCreationTime());
                act_exception_judgement_name.setText(data.getData().getApplicant());
                act_exception_judgement_organization.setText(data.getData().getProductionLineName());
                act_deal_exception_judgement_result_text.setText(data.getData().getHandleType());
                act_deal_exception_judgement_desc_text.setText(data.getData().getDescription());
            }

            @Override
            public void onDaojuHandeSucceed() {

            }

            @Override
            public void onOnProblemSucceed(String data) {
                setResult(200);
                EventBus.getDefault().post("40");
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
        if (exceptionId==-1){
            showSnackBar("没有获取到数据",2, Snackbar.LENGTH_LONG);
        }else{
            preException.requestProblemGet(exceptionId+"");
        }
    }

    @OnClick({R.id.act_deal_exception_gongjian_btn, R.id.act_deal_exception_daoju_btn, R.id.act_deal_exception_shebei_btn, R.id.act_deal_exception_normal_btn})
    @Override
    protected void OnViewClicked(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.act_deal_exception_gongjian_btn:
                intent = new Intent(ActExceptionJudgement.this, ActGenGongjianException.class);//工件异常通知
                break;
            case R.id.act_deal_exception_daoju_btn:
                intent = new Intent(ActExceptionJudgement.this, ActGenDaoJuException.class);//刀具异常通知
                break;
            case R.id.act_deal_exception_shebei_btn:
                intent = new Intent(ActExceptionJudgement.this, ActGenEquException.class);//设备异常通知

                break;
            case R.id.act_deal_exception_normal_btn:
                if (exceptionId==-1){
                    showSnackBar("没有获取到数据",2, Snackbar.LENGTH_LONG);
                }else{
                    preException.handProblem(exceptionId+"","40","","","","");

                }
                break;
        }
        if (intent != null) {
            intent.putExtra("id",exceptionId);
            intent.putExtra("lineId",lineId);
            startActivityForResult(intent,79);
//            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Debug.info(TAG,"onActivityResult异常提交成功返回");
        if (requestCode==79&&resultCode==200){
            setResult(200);
            EventBus.getDefault().post("40");
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

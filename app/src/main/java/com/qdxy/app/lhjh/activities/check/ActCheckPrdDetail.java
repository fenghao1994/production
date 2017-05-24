package com.qdxy.app.lhjh.activities.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
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
import com.qdxy.app.lhjh.activities.selectors.ActSelector;
import com.qdxy.app.lhjh.views.TempMyListView;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**产品抽检结果处理
 * Created by KY on 2016/11/30.
 */

public class ActCheckPrdDetail extends TempActivity implements ViewCheckI{
    private final String TAG ="ActCheckPrdDetail";
//    private RespCheckPrdList.DataBean.DatasBean detailData;
    @Bind(R.id.act_prd_check_detail_code)TextView act_prd_check_detail_code;
    @Bind(R.id.act_prd_check_person_name_text)TextView act_prd_check_person_name_text;
    @Bind(R.id.act_prd_check_self_listView)TempMyListView act_prd_check_self_listView;
    @Bind(R.id.act_prd_check_random_listView)TempMyListView act_prd_check_random_listView;
    @Bind(R.id.act_prd_check_checkbox0)AppCompatRadioButton act_prd_check_checkbox0;
    @Bind(R.id.act_prd_check_checkbox1)AppCompatRadioButton act_prd_check_checkbox1;
    @Bind(R.id.act_prd_check_checkbox2)AppCompatRadioButton act_prd_check_checkbox2;
    @Bind(R.id.act_prd_check_checkbox3)AppCompatRadioButton act_prd_check_checkbox3;
    @Bind(R.id.act_prd_check_checkbox4)AppCompatRadioButton act_prd_check_checkbox4;
    @Bind(R.id.act_prd_check_checkbox5)AppCompatRadioButton act_prd_check_checkbox5;
    @Bind(R.id.act_prd_check_commit_btn)AppCompatButton act_prd_check_commit_btn;
    @Bind(R.id.act_prd_check_random_operation_frame)LinearLayout act_prd_check_random_operation_frame;
    private int checkPosition=0;//抽检结果选择记录
    private AdapterCheckRandomDetail adapterCheckRandomDetail;
    private AdapterCheckSelfDetail adapterCheckSelfDetail;
    private PreCheck preCheck;
    private int personId;//责任人id
    private  int mRandomCheckTaskId;//抽检任务id
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_prd_handle_detail_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品抽检详情");
        preCheck = new PreCheck(this);
        act_prd_check_commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personId==-1){
                    showSnackBar("请选择责任人",2,Snackbar.LENGTH_LONG);
                }else{
                    preCheck.createRecord(mRandomCheckTaskId+"",checkPosition+"",personId+"");
                }
            }
        });
        act_prd_check_commit_btn.setEnabled(false);
//        detailData = (RespCheckPrdList.DataBean.DatasBean) MyApplication.getInstance().getExtralObj("checkData");
    }

    @Override
    protected void setListeners() {
        act_prd_check_checkbox0.setChecked(true);

        act_prd_check_checkbox0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox0.setChecked(true);
                checkPosition=0;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });
        act_prd_check_checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox1.setChecked(true);
                checkPosition=1;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });
        act_prd_check_checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox2.setChecked(true);
                checkPosition=2;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });
        act_prd_check_checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox3.setChecked(true);
                checkPosition=3;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });
        act_prd_check_checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox4.setChecked(true);
                checkPosition=4;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });
        act_prd_check_checkbox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckBox();
                act_prd_check_checkbox5.setChecked(true);
                checkPosition=5;
                Debug.info(TAG,"触发点击事件"+checkPosition);
            }
        });

    }
private void resetCheckBox(){
    act_prd_check_checkbox0.setChecked(false);
    act_prd_check_checkbox1.setChecked(false);
    act_prd_check_checkbox2.setChecked(false);
    act_prd_check_checkbox3.setChecked(false);
    act_prd_check_checkbox4.setChecked(false);
    act_prd_check_checkbox5.setChecked(false);

}
    @Override
    protected void bindValues() {
        mRandomCheckTaskId= getIntent().getIntExtra("id",-1);
        if (mRandomCheckTaskId!=-1){

            preCheck.requestRandomCheckDetail(mRandomCheckTaskId+"");
        }else{
            showSnackBar("没有获取到抽检任务数据",2, Snackbar.LENGTH_LONG);
        }

    }
@OnClick({R.id.act_prd_check_random_operation_frame})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_prd_check_random_operation_frame:
                startActivityForResult(new Intent(ActCheckPrdDetail.this, ActSelector.class),99);
                break;
        }
    }

    @Override
    public void onCreateRecordSucceed(String message) {
//        showToast(message);
        EventBus.getDefault().post("check_1");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==99&&resultCode==200&&data!=null){
            personId=data.getIntExtra("id",-1);
            String name=data.getStringExtra("name");
            act_prd_check_person_name_text.setText(name);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onCheckPrdDetailSucceed(RespCheckPrdDetail data) {
        Debug.info("抽检详情数据返回"+data.toString());
        act_prd_check_random_operation_frame.setVisibility(data.getData().isRandomCheck()?View.GONE:View.VISIBLE);
        act_prd_check_commit_btn.setEnabled(!data.getData().isRandomCheck());
//        act_prd_check_commit_btn.setVisibility(data.getData().isIsRandomCheck()?View.GONE:View.VISIBLE);
        act_prd_check_detail_code.setText(data.getData().getProductCode());
        adapterCheckRandomDetail = new AdapterCheckRandomDetail(data.getData().getRandomCheckList(),ActCheckPrdDetail.this,R.layout.item_check_prd_detail_layout);
        adapterCheckSelfDetail = new AdapterCheckSelfDetail(data.getData().getSelfCheckList(),ActCheckPrdDetail.this,R.layout.item_check_prd_detail_layout);
        act_prd_check_random_listView.setDividerHeight(0);
        act_prd_check_self_listView.setDividerHeight(0);
        act_prd_check_random_listView.setAdapter(adapterCheckRandomDetail);
        act_prd_check_self_listView.setAdapter(adapterCheckSelfDetail);
    }

    @Override
    public void onFailed(int who, String message) {

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
}

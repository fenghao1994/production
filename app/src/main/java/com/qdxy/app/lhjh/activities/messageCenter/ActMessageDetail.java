package com.qdxy.app.lhjh.activities.messageCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionDaoJuList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionEquList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionJiaGongList;
import com.qdxy.app.lhjh.activities.exceptions.ActExceptionJudgementList;

import butterknife.Bind;

/**
 * 消息详情
 * Created by KY on 2016/12/22.
 */

public class ActMessageDetail extends TempActivity {
    private final String TAG = "ActMessageDetail";
    private int id = -1, type = -1;
    private PreMessageCenter preMessageCenter;
    @Bind(R.id.act_exception_detail_index0_text)
    TextView index0;
    @Bind(R.id.act_exception_detail_index1_text)
    TextView index1;
    @Bind(R.id.act_exception_detail_index2_text)
    TextView index2;
    @Bind(R.id.act_exception_detail_index3_text)
    TextView index3;
    @Bind(R.id.act_exception_detail_deal_commit)
    AppCompatButton goToBtn;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_message_center_detail_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "消息详情");

    }

    @Override
    protected void setListeners() {
        preMessageCenter = new PreMessageCenter(new ViewMessageCenterI() {
            @Override
            public void onMesaageSucceed(RespMessageDetail data) {
                index0.setText(data.getData().getTitle());
                index1.setText(data.getData().getCreationTime());
                index2.setText(data.getData().getSenderName());
                index3.setText(data.getData().getContent());
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
        goToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                   0工件异常
//                1设备异常
//                2刀具异常
//                3超时报警异常
//                4处理超时
                Intent intent = null;
                switch (type) {
                    case 0:
                        intent = new Intent(ActMessageDetail.this, ActExceptionJiaGongList.class);
                        break;
                    case 1:
                        intent = new Intent(ActMessageDetail.this, ActExceptionEquList.class);
                        break;
                    case 2:
                        intent = new Intent(ActMessageDetail.this, ActExceptionDaoJuList.class);
                        break;
                    case 3:
                        intent = new Intent(ActMessageDetail.this, ActExceptionJudgementList.class);
                        break;
                    case 4:
                        intent = new Intent(ActMessageDetail.this, ActExceptionJudgementList.class);
                        break;

                }
                if (intent!=null){
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void bindValues() {

        goToBtn.setVisibility(TempLoginConfig.getPermissionStatus("Total.App.WorkingTimeoutProblem.Judge") || TempLoginConfig.getPermissionStatus("Total.App.DeviceProblem.Handle") || TempLoginConfig.getPermissionStatus("Total.App.DeviceToolProblem.Handle") || TempLoginConfig.getPermissionStatus("Total.App.MachiningPartProblem.Handle") ? View.VISIBLE : View.INVISIBLE);
        type = getIntent().getIntExtra("type", -1);
        id = getIntent().getIntExtra("id", -1);
        Debug.info(TAG, "type=" + type + "||id=" + id);
        if (id == -1) {
            showSnackBar("没有获取到消息id", 2, Snackbar.LENGTH_LONG);
        } else if (type == -1) {
            showSnackBar("没有获取到消息类型", 2, Snackbar.LENGTH_LONG);
        } else {
            preMessageCenter.systemMessageDetail(id);
        }

    }

    @Override
    protected void OnViewClicked(View v) {

    }
}

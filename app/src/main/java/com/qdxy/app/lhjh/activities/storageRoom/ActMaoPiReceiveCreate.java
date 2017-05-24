package com.qdxy.app.lhjh.activities.storageRoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.global.PreGlobal;
import com.qdxy.app.lhjh.activities.global.ViewGlobal;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 创建毛坯领用
 * Created by mac on 2017/2/21.
 */

public class ActMaoPiReceiveCreate extends TempActivity {
    private final String TAG = "ActMPStorageCreate";
    private List<RespStorageItem.DataBean> mTypeData, mFracoryData;
    /**
     * 生产数据
     */
    private List<RespProductLines.DataBean> linesData;
    private PreStorageRoom mPre;
    private int modelId = -1, manufactorId = -1, productionLineId = -1;
    private PreGlobal preGlobal;
    private ViewGlobal viewGlobal;
    @Bind(R.id.storage_spinner_index0)
    AppCompatSpinner mSpinner0;
    @Bind(R.id.storage_spinner_index1)
    AppCompatSpinner mSpinner1;
    @Bind(R.id.storage_spinner_index2)
    AppCompatSpinner mSpinner2;
    @Bind(R.id.storage_check_index2)
    TextView sendTimeText;//发货时间
    @Bind(R.id.storage_editText_index3)
    AppCompatEditText mEdittextCount;
    @Bind(R.id.storage_editText_index4)
    AppCompatEditText mEdittextRemark;

    @Bind(R.id.storage_commit_btn)
    AppCompatButton mCommitBtn;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_mp_receive_create_layout);
    }


    @Override
    protected void findViews() {
        initToolbar("创建毛坯领用订单");
        mSpinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelId = mTypeData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manufactorId = mFracoryData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productionLineId = linesData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void setListeners() {

        viewGlobal = new ViewGlobal() {
            @Override
            public void onGetProductionLinesSucceed(RespProductLines data) {
                Debug.info("获取到生产线数据" + data.toString());
//                linesData = data;
                ArrayList<String> items0 = new ArrayList<>();
                linesData = data.getData();
                if (linesData != null) {
                    for (int i = 0; i < linesData.size(); i++) {
                        items0.add(linesData.get(i).getName());
                    }
                }
                ArrayAdapter<String> tempAdapter1 = new ArrayAdapter<>(getTempContext(), android.R.layout.simple_spinner_item, items0);
                tempAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner2.setAdapter(tempAdapter1);
            }

            @Override
            public void onGetProductionLinesFailed(TempErrorCode code, String message) {
                superViewMessage(message);
            }

            @Override
            public void onGetProductTypeSucceed(RespProductModel data) {
                Debug.info("获取到产品型号数据" + data.toString());
            }

            @Override
            public void onGetProductTypeFailed(TempErrorCode code, String message) {
                superViewMessage(message);
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
        preGlobal = new PreGlobal(viewGlobal);
        mPre = new PreStorageRoom(new ViewStorageRoom() {

            @Override
            public <RESULT> void onSucceed(RESULT result, int type) {

                if (type == 10) {
                    ArrayList<String> items0 = new ArrayList<>();
                    Debug.info(TAG, "返回毛坯类型成功");
                    mTypeData = ((RespStorageItem) result).getData();
                    if (mTypeData != null) {
                        for (int i = 0; i < mTypeData.size(); i++) {
                            items0.add(mTypeData.get(i).getName());
                        }
                    }
                    ArrayAdapter<String> tempAdapter1 = new ArrayAdapter<>(getTempContext(), android.R.layout.simple_spinner_item, items0);
                    tempAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner0.setAdapter(tempAdapter1);
                } else if (type == 11) {
                    Debug.info(TAG, "返回厂家数据成功");
                    ArrayList<String> items1 = new ArrayList<>();
                    mFracoryData = ((RespStorageItem) result).getData();
                    if (mFracoryData != null) {
                        for (int i = 0; i < mFracoryData.size(); i++) {
                            items1.add(mFracoryData.get(i).getName());
                        }
                    }
                    ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(getTempContext(), android.R.layout.simple_spinner_item, items1);
                    tempAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner1.setAdapter(tempAdapter2);
                } else if (type == 12) {
//                    Intent tempIntent = getIntent();
//                    tempIntent.putExtra("result");
                    setResult(200);
                    finish();
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
        if (linesData == null) {
            preGlobal.requestProductedLines();
        }
        mPre.getRoughcastType();
        mPre.getRoughcastFactory();
    }

    @OnClick({R.id.storage_commit_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.storage_commit_btn:
                if (modelId == -1) {
                    showSnackBar("没有型号数据！", 2, Snackbar.LENGTH_LONG);
                } else if (manufactorId == -1) {
                    showSnackBar("没有厂商数据！", 2, Snackbar.LENGTH_LONG);
                } else if (productionLineId == -1) {
                    showSnackBar("没有型号数据！", 2, Snackbar.LENGTH_LONG);
                } else if ("".equals(mEdittextCount.getText().toString().trim())) {
                    showSnackBar("请输入数量！", 2, Snackbar.LENGTH_LONG);
                } else {

                    mPre.orderCreate(manufactorId, modelId,  Integer.valueOf(mEdittextCount.getText().toString().trim()),productionLineId, mEdittextRemark.getText().toString().trim(), "2017-03-02 10:20:00");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Debug.info(TAG, "进入activity回掉");
    }
}

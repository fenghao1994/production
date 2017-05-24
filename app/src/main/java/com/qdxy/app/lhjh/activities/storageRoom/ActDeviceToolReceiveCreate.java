package com.qdxy.app.lhjh.activities.storageRoom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
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
import de.greenrobot.event.EventBus;

/**
 * 创建刀具领用订单
 * Created by mac on 2017/2/27.
 */

public class ActDeviceToolReceiveCreate extends TempActivity {
    private final String TAG = "ActMaterialCreate";
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_add)
    TextView mTvAdd;
    /**
     * 生产数据
     */
    private List<RespProductLines.DataBean> linesData;
    private int mModleId = -1;
    private String mModleName;
    private PreStorageRoom mPre;
    private PreGlobal preGlobal;
    private TempRVCommonAdapter<RequestBodyMaterial.ItemsBody> mAdapter;
    private RequestBodyMaterial mRequestData;//请求数据
    private AppCompatSpinner mMaterialSpinner;
    private TextView mMaterialCountText;
    private List<RespStorageItem.DataBean> mTypeData;
    @Bind(R.id.storage_spinner_index2)
    AppCompatSpinner mSpinner2;
    //    @Bind(R.id.image_add)
    //    ImageView image_add;
    @Bind(R.id.act_material_receiverView)
    RecyclerView act_material_receiverView;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_material_create_layout);
    }

    @Override
    protected void findViews() {
        initToolbar("创建刀具领用订单");
        mTvName.setText("刀具列表");
        mTvAdd.setText("添加刀具");

    }

    @Override
    protected void setListeners() {
        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRequestData.setProductionLineId(linesData.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        preGlobal = new PreGlobal(new ViewGlobal() {
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
        });
        mPre = new PreStorageRoom(new ViewStorageRoom() {

            @Override
            public <RESULT> void onSucceed(RESULT result, int type) {

                if (type == 10) {
                    Debug.info(TAG, "刀具数据返回");
                    mTypeData = ((RespStorageItem) result).getData();
                    showCreateMaterialDialog();

                } else if (type == 12) {
                    //                    Intent tempIntent = getIntent();
                    //                    tempIntent.putExtra("result");
                    //                    setResult(200);
                    EventBus.getDefault().post("succeed");
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

        preGlobal.requestProductedLines();
        initAdapter();

    }

    @OnClick({R.id.storage_commit_btn, R.id.tv_add})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.storage_commit_btn:
                //                RequestBodyMaterial body = new RequestBodyMaterial();
                //                List<RequestBodyMaterial.ItemsBody> items = new ArrayList<>();
                //                for (int i = 0; i < 2; i++) {
                //                    RequestBodyMaterial.ItemsBody item = new RequestBodyMaterial.ItemsBody();
                //                    item.setCount(1);
                //                    item.setModelId(2 + i);
                //                    items.add(item);
                //                }
                //                body.setItems(items);
                //                body.setProductionLineId(1005);
                if (mRequestData.getProductionLineId() == -1) {
                    showSnackBar("没有生产线数据", 2, Snackbar.LENGTH_LONG);
                } else if (mRequestData.getItems().isEmpty()) {
                    showSnackBar("请添加刀具", 2, Snackbar.LENGTH_LONG);
                } else {

                    mPre.orderCreate_deviceTool(mRequestData);
                }
                break;
            case R.id.tv_add:
                //添加辅料
                if (mTypeData == null) {

                    mPre.getDeviceToolType();//获取刀具数据
                } else {
                    showCreateMaterialDialog();
                }

                break;
        }
    }

    private void showCreateMaterialDialog() {
        ArrayList<String> items0 = new ArrayList<>();
        //        mTypeData = result.getData();
        if (mTypeData != null) {
            for (int i = 0; i < mTypeData.size(); i++) {
                items0.add(mTypeData.get(i).getName());
            }
        }
        ArrayAdapter<String> tempAdapter1 = new ArrayAdapter<>(getTempContext(), android.R.layout.simple_spinner_item, items0);
        tempAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.body_material_create_layout, null);
        TextView title = (TextView) rootView.findViewById(R.id.body_material_title);
        title.setText("刀具添加");
        mMaterialSpinner = (AppCompatSpinner) rootView.findViewById(R.id.body_material_spinner);
        mMaterialSpinner.setAdapter(tempAdapter1);
        mMaterialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModleId = mTypeData.get(position).getId();
                mModleName = mTypeData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mMaterialCountText = (TextView) rootView.findViewById(R.id.body_material_text);
        new AlertDialog.Builder(this).setView(rootView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mModleId == -1) {
                    showSnackBar("没有刀具数据", 2, Snackbar.LENGTH_LONG);
                } else if (mMaterialCountText.getText().toString().trim().equals("")) {
                    showSnackBar("请输入数量", 2, Snackbar.LENGTH_LONG);
                } else {
                    RequestBodyMaterial.ItemsBody itemsBody = new RequestBodyMaterial.ItemsBody();
                    itemsBody.setModelId(mModleId);
                    itemsBody.setName(mModleName);
                    itemsBody.setCount(Integer.valueOf(mMaterialCountText.getText().toString().trim()));
                    mRequestData.getItems().add(itemsBody);
                    //                    mAdapter.notifyDataSetChanged();
                    mAdapter.updateRefresh(mRequestData.getItems());
                }
                mModleId = -1;//重置选中id
            }
        }).setNegativeButton("取消", null).show();
    }

    private void initAdapter() {
        act_material_receiverView.setLayoutManager(new LinearLayoutManager(getTempContext()));
        act_material_receiverView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TempRVCommonAdapter<RequestBodyMaterial.ItemsBody>(getTempContext(), R.layout.item_material_create_copy_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, final RequestBodyMaterial.ItemsBody itemsBody) {
                holder.setVisible(R.id.item_material_image_status, false);
                holder.setText(R.id.item_material_text_name, "名称：" + itemsBody.getName());
                holder.setText(R.id.item_material_text_count, "数量：" + itemsBody.getCount());
                holder.setOnClickListener(R.id.img_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(ActDeviceToolReceiveCreate.this).setTitle("是否确认删除该刀具?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mAdapter.remove(itemsBody);
                                mRequestData.getItems().remove(itemsBody);

                            }
                        }).setNegativeButton("取消", null).show();
                    }
                });
            }
        };

        act_material_receiverView.setAdapter(mAdapter);
        mRequestData = new RequestBodyMaterial();
        //初始化请求数据
        mRequestData.setProductionLineId(-1);
        List<RequestBodyMaterial.ItemsBody> items = new ArrayList<>();
        mRequestData.setItems(items);

    }
}

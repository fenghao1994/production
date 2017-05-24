package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.emues.CommonType;

/**毛坯入库申请详情
 * Created by mac on 2017/2/20.
 */

public class ActStorageDetail extends TempActivity {
    private CommonType mType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
            setContentView(R.layout.act_storage_detail_layout);
    }

    @Override
    protected void findViews() {
//        initToolbar("毛坯入库申请详情");
        mType = (CommonType) getIntent().getSerializableExtra("type");
        switch (mType) {
            case MAO_PI_STORAGE_SQ:
                initToolbar("毛坯入库申请详情");
                break;
            case MAO_PI_STORAGE_CJ:
                initToolbar("毛坯入库抽检详情");
                break;
            case MAO_PI_STORAGE_SP:
                initToolbar("毛坯入库审批详情");
                break;
            case MAO_PI_LY:
            case MAO_PI_STORAGE_LY:
                initToolbar("毛坯领用详情");
                break;
            case MATERIAL_SQ:
            case MATERIAL_FH:
            case MATERIAL_SP:
                initToolbar("辅料领用详情");
                break;
            default:
                initToolbar(getResources().getString(R.string.app_name));
        }

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        //启动fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragTempDetail titleFragment = new FragTempDetail();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putSerializable("type", mType);//类型
        bundlePrdUnchecked.putInt("id", getIntent().getIntExtra("id",-1));//类型
        titleFragment.setArguments(bundlePrdUnchecked);
        fragmentTransaction.add(R.id.act_storage_detail_fragFrame, titleFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void OnViewClicked(View v) {

    }
}

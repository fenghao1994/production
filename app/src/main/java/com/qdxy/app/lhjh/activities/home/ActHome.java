package com.qdxy.app.lhjh.activities.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempViews.TempNestingGridView;
import com.lf.tempcore.tempViews.tempPullableViews.PullToRefreshLayout;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.login.ActLogin;
import com.qdxy.app.lhjh.activities.messageCenter.ActMessageCenter;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 首页
 * Created by KY on 2016/10/13.
 */

public class ActHome extends TempActivity implements NavigationView.OnNavigationItemSelectedListener, ViewHome {
    private final String TAG = ActHome.class.getSimpleName();
    private LinearLayout act_home_rootView;
    private DrawerLayout mDrawer;
    private PreHome mPreHome;
    NavigationView navigationView;
    @Bind(R.id.act_home_pullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;
    TextView name_text;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        Debug.info(TAG, "initContentView");
        setContentView(R.layout.act_home_layout);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTop.setTitle("");
        TextView mTitle = (TextView) toolbarTop.findViewById(com.lf.tempcore.R.id.toolbar_title);
        if (mTitle != null)
            mTitle.setText("力海俊珲生产管理");

        ImageView messageImage = (ImageView) toolbarTop.findViewById(R.id.toolbar_menu);
        messageImage.setVisibility(View.VISIBLE);
        messageImage.setImageResource(R.mipmap.icon_massage);
        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TempLoginConfig.sf_getLoginState()) {
                    showLoginDialog();
                } else {

                    startActivity(new Intent(ActHome.this, ActMessageCenter.class));
                }
            }
        });
        setSupportActionBar(toolbarTop);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbarTop, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        name_text = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name_text);
//        TextView name_text= (TextView) findViewById(R.id.nav_header_name_text);
        name_text.setText(TempLoginConfig.sf_getUserName());
//        if (TempLoginConfig.sf_getWorkStatus() == 0) {
//            navigationView.getMenu().getItem(0).setTitle("下班");
//        } else {
//            navigationView.getMenu().getItem(0).setTitle("上班");
//        }

        navigationView.setNavigationItemSelectedListener(this);
        //获取菜单和权限数据
        mPreHome = new PreHome(this);
        if (TempLoginConfig.sf_getLoginState()) {
            navigationView.getMenu().getItem(1).setTitle("退出登录");
            mPreHome.requestHomeMenu(TempLoginConfig.sf_getUserId(), TempLoginConfig.sf_getToken());
            mPreHome.requestPermission();
        } else {
            navigationView.getMenu().getItem(1).setTitle("登录");
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Debug.info(TAG, "onNewIntent");
        //获取菜单和权限数据

    }

    private void showLoginDialog() {
        showConfirmationDialog(ActHome.this, false, "登录提醒", "是否前往登录页面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Debug.info(TAG, "用户未登陆,跳转到登陆页面");
                startActivityForResult(new Intent(ActHome.this, ActLogin.class), 10);
            }
        }, null);
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(getApplicationContext());
        super.onResume();
        Debug.info(TAG, "onResume");
        if (TempLoginConfig.sf_getWorkStatus() == 0) {
            navigationView.getMenu().getItem(0).setTitle("下班");
        } else {
            navigationView.getMenu().getItem(0).setTitle("上班");
        }
        if (!TempLoginConfig.sf_getLoginState()) {
            showLoginDialog();
        } else {
            //极光推送注册
            String userID = TempLoginConfig.sf_getUserId();
            Debug.info(TAG, "推送注册id="+userID);
            if (!TextUtils.isEmpty(userID) && !TempLoginConfig.getJPust_status(userID)) {
                Debug.info(TAG, "注册id=" + userID);
                JPushInterface.setAliasAndTags(getApplicationContext(), TempLoginConfig.sf_getUserId(), null, new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                        switch (arg0) {
                            case 0:
                                Debug.info(TAG, "极光设置别名成功userID=" + TempLoginConfig.sf_getUserId());
                                TempLoginConfig.sf_saveJPust_status(TempLoginConfig.sf_getUserId(), true);
                                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                break;
                            default:
                                showToast("推送别名注册失败，请重新登录");
                                break;
                        }
                    }
                });
            }else{
                Debug.info(TAG, "极光推送已注册，id="+userID);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Debug.info(TAG, "onActivityResult requestCode=" + requestCode);
        name_text.setText("未登录");
        navigationView.getMenu().getItem(1).setTitle("登录");
        act_home_rootView.removeAllViews();
        if (requestCode == 10 && resultCode == 200) {
            name_text.setText(TempLoginConfig.sf_getUserName());
            showSnackBar("登录成功!", 0, Snackbar.LENGTH_LONG);
            navigationView.getMenu().getItem(1).setTitle("退出登录");
            mPreHome.requestHomeMenu(TempLoginConfig.sf_getUserId(), TempLoginConfig.sf_getToken());
            mPreHome.requestPermission();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(getApplicationContext());
        Debug.info(TAG, "onResume");
    }


    @Override
    protected void findViews() {
        act_home_rootView = (LinearLayout) findViewById(R.id.act_home_rootView);
    }

    @Override
    protected void setListeners() {
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mPreHome.requestHomeMenu(TempLoginConfig.sf_getUserId(), TempLoginConfig.sf_getToken());
                mPreHome.requestPermission();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
        pullToRefreshLayout.setPullUpEnable(false);
    }

    @Override
    protected void bindValues() {

    }

    @Override
    protected void OnViewClicked(View v) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.drawer_menu_exit:
                Debug.info(TAG, "点击登录/退出登录");
                if (!TempLoginConfig.sf_getLoginState()) {
                    startActivityForResult(new Intent(ActHome.this, ActLogin.class), 10);
//                    Debug.info(TAG,"用户未登陆,跳转到登陆页面");

                } else {
                    JPushInterface.setAliasAndTags(getApplicationContext(), "", null, new TagAliasCallback() {

                        @Override
                        public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            switch (arg0) {
                                case 0:
                                    Debug.info(TAG, "极光取消别名成功");
                                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                    break;
                            }
                        }
                    });
                    mPreHome.userLogout(TempLoginConfig.sf_getUserId());
                }

                break;
            case R.id.drawer_menu_xiaban:
                if (!TempLoginConfig.sf_getLoginState()) {
                    showLoginDialog();
                } else if (item.getTitle().equals("上班")) {
                    mPreHome.requestWorkStatus(0 + "");
                    item.setTitle("下班");
                } else {
                    mPreHome.requestWorkStatus(1 + "");
                    item.setTitle("上班");
                }
                break;
        }
       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }*/

        if (mDrawer != null) {

            mDrawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void viewMenuSucceed(RespHomeMenuGroup data) {
        TextView name_text = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name_text);
//        TextView name_text= (TextView) findViewById(R.id.nav_header_name_text);
        name_text.setText(TempLoginConfig.sf_getUserName());
        if (TempLoginConfig.sf_getWorkStatus() == 0) {
            navigationView.getMenu().getItem(0).setTitle("下班");
        } else {
            navigationView.getMenu().getItem(0).setTitle("上班");
        }

        act_home_rootView.removeAllViews();
        Debug.info(TAG, "获取到菜单数据成功" + data.toString());
        List<RespHomeMenuGroup.DataBean> itemDatas = data.getData();
        for (int i = 0; i < itemDatas.size(); i++) {
            List<RespMenuChild> childList = itemDatas.get(i).getChildren();
            /*if (childList == null || childList.isEmpty()) {
                continue;
            }*/
            initGridData(itemDatas.get(i));
        }
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLogoutSucceed() {
        TempLoginConfig.sf_saveLoginState(false);
        startActivityForResult(new Intent(ActHome.this, ActLogin.class), 10);
    }

    @Override
    public void onWorkSucceed() {
        mPreHome.requestHomeMenu(TempLoginConfig.sf_getUserId(), TempLoginConfig.sf_getToken());
    }

    @Override
    public void onWorkFailed() {
        act_home_rootView.removeAllViews();
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
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

    }

    @Override
    public void viewMsg(String message) {
        superViewMessage(message);
    }

    @Override
    public void viewError(TempErrorCode code, String message) {
        superViewError(code, message);
    }

    /**
     * 初始化option GridView 数据
     */
    private void initGridData(RespHomeMenuGroup.DataBean groupData) {
        View rawView = getLayoutInflater().inflate(R.layout.item_home_row_layout, null);
        TextView groupName = (TextView) rawView.findViewById(R.id.item_home_group_name);
        TempNestingGridView gridView = (TempNestingGridView) rawView.findViewById(R.id.item_home_chile_gridView);

        groupName.setText(TextUtils.isEmpty(groupData.getTitle()) ? "" : groupData.getTitle());
        List<RespMenuChild> childData = groupData.getChildren();
        OptionViewAdapter adapter = new OptionViewAdapter(childData, ActHome.this, R.layout.item_home_option_layout);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new GridClickListener(childData) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                super.onItemClick(parent, view, position, id);
            }
        });
        act_home_rootView.addView(rawView);
    }

    public abstract class GridClickListener implements AdapterView.OnItemClickListener {
        List<RespMenuChild> mItemData;

        public GridClickListener(List<RespMenuChild> itemData) {
            mItemData = itemData;

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showToast("点击Action="+mItemData.get(position).getAction());
            try {
//                if (mItemData.get(position).getActivity().equals("product.ActProduction")){
//                    startActivity(new Intent(ActHome.this, Class.forName("com.qdxy.app.lhjh.activities.product.ActProduction2" )));
//                    startActivity(new Intent(ActHome.this, Class.forName("com.qdxy.app.lhjh.activities.product.ActInBox" )));
//                }else{

                    startActivity(new Intent(ActHome.this, Class.forName("com.qdxy.app.lhjh.activities." + mItemData.get(position).getActivity())));
//                }
            } catch (ClassNotFoundException e) {
                superViewError(TempErrorCode.RUN_TIME_EXCEPTION, "跳转页面未找到");
                e.printStackTrace();

            } catch (ActivityNotFoundException e) {
                superViewError(TempErrorCode.RUN_TIME_EXCEPTION, "跳转页面未注册");
                e.printStackTrace();
            } catch (Exception e) {
                superViewError(TempErrorCode.RUN_TIME_EXCEPTION, "跳转页面出现异常，请联系管理员");
                e.printStackTrace();
            }
          /*  switch (mItemData.get(position).getAction()){
                case "异常判断":
//                    startActivity(new Intent(ActHome.this,ActExceptionJudgementList.class));
                    try {
                        startActivity(new Intent(ActHome.this,Class.forName(mItemData.get(position).getAction())));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
//                    startActivity(new Intent(ActHome.this,ActExceptionJudgementList.class));
                    break;
                case "产品加工":
                    startActivity(new Intent(ActHome.this,ActProduction.class));
                    break;
                case "加工异常":
                    startActivity(new Intent(ActHome.this,ActExceptionJiaGongList.class));
                    break;
                case "设备异常":
                    startActivity(new Intent(ActHome.this,ActExceptionEquList.class));
                    break;
                case "刀具异常":
                    startActivity(new Intent(ActHome.this,ActExceptionDaoJuList.class));
                    break;
                case "批次管理":
                    startActivity(new Intent(ActHome.this,ActPiCiManage.class));
                    break;
            }*/
            /*if (position==0){

                startActivity(new Intent(ActHome.this,ActProduction.class));//跳转到加工页面
            } else if (position==1) {
                startActivity(new Intent(ActHome.this,ActExceptionJudgementList.class));//跳转到加工页面
            }*/
        }
    }

    //
    private class OptionViewHolder {
        private TextView name;
        private ImageView image;

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }
    }

    public class OptionViewAdapter extends TempListAdapter<RespMenuChild, OptionViewHolder> {
        public OptionViewAdapter(List data, Context context, int layoutId) {
            super(data, context, layoutId);
        }

        @Override
        protected OptionViewHolder createHolder() {
            return new OptionViewHolder();
        }

        @Override
        protected void initHolder(int position, View v, OptionViewHolder holder) {
            holder.setImage((ImageView) v.findViewById(R.id.item_home_option_child_image));
            holder.setName((TextView) v.findViewById(R.id.item_home_option_child_name));
        }

        @Override
        public void bunldHolderValue(int position, OptionViewHolder holder, RespMenuChild item) {
            if (!TextUtils.isEmpty(item.getIcon())) {
                Glide.with(getTempContext()).load("file:///android_asset/" + item.getIcon()).error(R.mipmap.ic_launcher).into(holder.getImage());
            }
//            holder.getImage().setimager
            holder.getName().setText(item.getTitle());
        }
    }
}

package com.pipipan.demo.ui.fragment;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import butterknife.BindView;

public class FragmentUserLogin extends MyLazyFragment
                                implements XCollapsingToolbarLayout.OnScrimsListener{
    private static final String TAG = "FragmentUserLogin";
    static FragmentUserLogin fragmentUserLogin = null;

    public static FragmentUserLogin getInstance(){
        if (fragmentUserLogin == null){
            synchronized (FragmentUserLogin.class){
                if (fragmentUserLogin == null)
                    fragmentUserLogin = new FragmentUserLogin();
            }
        }
        return fragmentUserLogin;
    }


    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login)
    Button button;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.logout)
    RelativeLayout logout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_login;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void initView() {
        ImmersionBar.setTitleBar(getFragmentActivity(), toolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);

        logout.setOnClickListener((v -> {
            Log.i(TAG, "initView: logout");
            AlertDialog.Builder builder = new AlertDialog.Builder(getFragmentActivity());
            builder.setMessage("确定退出登录？")
                    .setNegativeButton("取消", (dialog, witch) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("确认", (dialog, witch) ->{
                        CommonUtil.saveSharedPreference(getContext(), "user_id", "");
                        Constants.user_id = "";
                        Intent intent = new Intent("login");
                        intent.putExtra("isLogin", false);
                        getFragmentActivity().sendBroadcast(intent);
                        });
            builder.show();
        }));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            titleBar.setTitle("我的");
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            titleBar.setTitle("");
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }
}

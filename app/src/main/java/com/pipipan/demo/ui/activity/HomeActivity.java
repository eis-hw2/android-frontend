package com.pipipan.demo.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.hjq.toast.ToastUtils;
import com.pipipan.base.BaseFragmentAdapter;
import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.helper.ActivityStackManager;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.helper.DoubleClickHelper;
import com.pipipan.demo.ui.adapter.MyAdapter;
import com.pipipan.demo.ui.fragment.FragmentUser;
import com.pipipan.demo.ui.fragment.FragmentUserLogin;
import com.pipipan.demo.ui.fragment.TestFragmentA;
import com.pipipan.demo.ui.fragment.TestFragmentB;
import com.pipipan.demo.ui.fragment.TestFragmentC;
import com.pipipan.demo.ui.fragment.TestFragmentD;
import com.pipipan.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 主页界面
 */
public class HomeActivity extends MyActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    BroadcastReceiver receiver;

    private MyAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.addOnPageChangeListener(this);
//        mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new MyAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        // 初始化图片加载器
        ImageLoader.init(getApplication());

        // 初始化吐司工具类
        ToastUtils.init(getApplication());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("login");
        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra("isLogin", true)){
                    mPagerAdapter.refresh(3, new FragmentUserLogin());
                    Log.i(TAG, "onReceive: login success");
                    Log.i(TAG, mPagerAdapter.getAllFragment().toString());
                }
                else {
                    mPagerAdapter.refresh(3, new FragmentUser());
                    Log.i(TAG, "onReceive: logout success");
                    Log.i(TAG, mPagerAdapter.getAllFragment().toString());
                }
            }
        };
        registerReceiver(receiver, intentFilter);

        Constants.user_id = CommonUtil.getStringFromSharedPreference(getContext(), "user_id");
        if (!Constants.user_id.isEmpty()){
            //TODO 拉取用户信息并存入Constants.user
            sendBroadcast(new Intent("login"));
        }
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_message);
                break;
            case 3:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                //mViewPager.setCurrentItem(0);
                //mViewPager.setCurrentItem(0, false);
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                mViewPager.setCurrentItem(0, mViewPager.getCurrentItem() == 1);
                return true;
            case R.id.home_found:
                //mViewPager.setCurrentItem(1);
                //mViewPager.setCurrentItem(1, false);
                mViewPager.setCurrentItem(1, mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 2);
                return true;
            case R.id.home_message:
                //mViewPager.setCurrentItem(2);
                //mViewPager.setCurrentItem(2, false);
                mViewPager.setCurrentItem(2, mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 3);
                return true;
            case R.id.home_me:
                //mViewPager.setCurrentItem(3);
                //mViewPager.setCurrentItem(3, false);
                mViewPager.setCurrentItem(3, mViewPager.getCurrentItem() == 2);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getHandler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }
}
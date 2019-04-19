package com.pipipan.demo.ui.activity;
import android.support.v7.widget.Toolbar;

        import com.hjq.bar.TitleBar;
        import com.pipipan.demo.R;
        import com.pipipan.demo.common.MyActivity;
        import com.pipipan.demo.widget.XCollapsingToolbarLayout;

        import butterknife.BindView;

public class OrderUserDetailActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_user_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {

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

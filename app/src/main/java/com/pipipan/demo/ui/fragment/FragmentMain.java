package com.pipipan.demo.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.helper.GlideImageLoader;
import com.pipipan.demo.ui.adapter.StoreAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;
import com.pipipan.image.ImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目炫酷效果示例
 */
public class FragmentMain extends MyLazyFragment
        implements XCollapsingToolbarLayout.OnScrimsListener {
    List<String> images = new ArrayList<>();

    @BindView(R.id.abl_test_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;
    @BindView(R.id.tb_test_a_bar)
    TitleBar mTitleBar;
    @BindView(R.id.address_image)
    ImageView addressImage;
    @BindView(R.id.tv_test_address)
    TextView mAddressView;
    @BindView(R.id.convenientBanner)
    Banner banner;
    @BindView(R.id.stores)
    RecyclerView recyclerView;

    StoreAdapter storeAdapter;

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        // 给这个ToolBar设置顶部内边距，才能和TitleBar进行对齐
        ImmersionBar.setTitleBar(getFragmentActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
    }

    @Override
    protected void initData() {
        loadTestDatas();
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        storeAdapter = new StoreAdapter(getContext(), getStoreList());
        recyclerView.setAdapter(storeAdapter);
    }

    private List<Store> getStoreList() {
        List<Store> stores = new ArrayList<>();
        for (int i=0; i<30; ++i)
            stores.add(new Store());
        return stores;

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

    /**
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            mAddressView.setTextColor(getResources().getColor(R.color.black));
            addressImage.setImageResource(R.mipmap.address);
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            mAddressView.setTextColor(getResources().getColor(R.color.white));
            addressImage.setImageResource(R.mipmap.address_white);
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }

    private void loadTestDatas() {
        //图片可能过期哦，自己换来测试吧
        images.add("http://image.tupian114.com/20181120/12097358.jpg");
        images.add("https://png.pngtree.com/png-clipart/20190116/ourlarge/pngtree-food-delicious-food-and-drink-a-bowl-of-rice-png-image_395602.jpg");
        images.add("http://bpic.588ku.com/element_origin_min_pic/19/03/07/26beb9fd1569c15e3da4f5b05bc5b77f.jpg");
    }
}
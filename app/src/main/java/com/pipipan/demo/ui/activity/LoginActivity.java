package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.helper.InputTextHelper;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 登录界面
 */
public class LoginActivity extends MyActivity {

    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;

    @BindView(R.id.btn_login_commit)
    Button mCommitView;

    private InputTextHelper mInputTextHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        mInputTextHelper = new InputTextHelper(mCommitView);
        mInputTextHelper.addViews(mPhoneView, mPasswordView);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivity(RegisterActivity.class);

//        startActivityForResult(new Intent(this, RegisterActivity.class), new ActivityCallback() {
//
//            @Override
//            public void onActivityResult(int resultCode, @Nullable Intent data) {
//                toast(String.valueOf(resultCode));
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    @OnClick({R.id.tv_login_forget, R.id.btn_login_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.btn_login_commit:
                //TODO 调用服务器API验证登录
                CommonUtil.saveSharedPreference(getContext(), "user_id", "皮皮潘");
                Intent intent = new Intent("login");
                sendBroadcast(intent);
                finish();
            default:
                break;
        }
    }
}
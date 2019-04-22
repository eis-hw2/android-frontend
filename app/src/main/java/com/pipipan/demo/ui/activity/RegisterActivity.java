package com.pipipan.demo.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.User;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.helper.InputTextHelper;
import com.pipipan.demo.network.Network;
import com.pipipan.widget.CountdownView;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 注册界面
 */
public class RegisterActivity extends MyActivity {
    private static final String TAG = "RegisterActivity";

    @BindView(R.id.et_register_phone)
    EditText mPhoneView;
    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;

    @BindView(R.id.et_register_code)
    EditText mCodeView;

    @BindView(R.id.et_register_password1)
    EditText mPasswordView1;
    @BindView(R.id.et_register_password2)
    EditText mPasswordView2;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.btn_register_commit)
    Button mCommitView;

    private InputTextHelper mInputTextHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_register_title;
    }

    @Override
    protected void initView() {
        mInputTextHelper = new InputTextHelper(mCommitView);
        mInputTextHelper.addViews(mPhoneView, mCodeView, mPasswordView1, mPasswordView2, name);
    }

    @Override
    protected void initData() {
//        getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finishResult(RESULT_OK);
//            }
//        }, 2000);
    }

    @OnClick({R.id.cv_register_countdown, R.id.btn_register_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_register_countdown: //获取验证码

                if (mPhoneView.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    toast(getResources().getString(R.string.phone_input_error));
                    break;
                }

                toast(getResources().getString(R.string.countdown_code_send_succeed));

                break;
            case R.id.btn_register_commit: //提交注册

                if (mPhoneView.getText().toString().length() != 11) {
                    toast(getResources().getString(R.string.phone_input_error));
                    break;
                }

                if (!mPasswordView1.getText().toString().equals(mPasswordView2.getText().toString())) {
                    toast(getResources().getString(R.string.two_password_input_error));
                    break;
                }
                User user = new User();
                user.setPhone(mPhoneView.getText().toString());
                user.setPassword(mPasswordView1.getText().toString());
                user.setUsername(name.getText().toString());
                Network.getInstance().logup(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();
                        Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(u));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }
}
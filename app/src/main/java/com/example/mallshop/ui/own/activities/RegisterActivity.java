package com.example.mallshop.ui.own.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.bean.RegisterBean;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.presenter.own.RegisterPresenter;
import com.example.mallshop.utils.MyUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<IOwn.IRegisterPresenter> implements IOwn.IRegisterView {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.layout_username)
    LinearLayout layoutUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.layout_password)
    LinearLayout layoutPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected IOwn.IRegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.img_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_register:
                String name = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)){
                    Map<String,String> map = new HashMap<>();
                    map.put("username",name);
                    map.put("password",pwd);
                    presenter.register(map);
                }else {
                    MyUtil.toast(this,"用户名或密码不能为空");
                }
                break;
        }
    }

    @Override
    public void registerResult(RegisterBean registerBean) {
        if (registerBean.getErrno() == 1000){
            MyUtil.toast(this,registerBean.getErrmsg());
        }else {
            MyUtil.toast(this,"注册成功,返回登录");
            finish();
        }
    }
}

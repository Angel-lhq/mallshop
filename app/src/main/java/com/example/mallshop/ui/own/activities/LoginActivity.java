package com.example.mallshop.ui.own.activities;

import android.content.Intent;
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
import com.example.mallshop.bean.LoginBean;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.presenter.own.LoginPresenter;
import com.example.mallshop.utils.LogUtil;
import com.example.mallshop.utils.MyUtil;
import com.example.mallshop.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<IOwn.ILoginPresenter> implements IOwn.ILoginView {
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
    protected IOwn.ILoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.img_back, R.id.btn_login,R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setResult(200);
                finish();
                break;
            case R.id.btn_login:
                String name = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
                    Map<String,String> map = new HashMap<>();
                    map.put("username",name);
                    map.put("password",password);
                    presenter.login(map);
                }else {
                    MyUtil.toast(this,"用户名或密码不能为空");
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void loginResult(LoginBean loginBean) {
        if (loginBean.getData().getCode() == 200){
            MyUtil.toast(this,"登陆成功");
            SpUtils.getInstance().setValue("username",loginBean.getData().getUserInfo().getUsername());
            SpUtils.getInstance().setValue("token",loginBean.getData().getToken());
            SpUtils.getInstance().setValue("uid",loginBean.getData().getUserInfo().getUid());
            SpUtils.getInstance().setValue("avatar",loginBean.getData().getUserInfo().getAvatar());
            SpUtils.getInstance().setValue("birthday",loginBean.getData().getUserInfo().getBirthday());
            SpUtils.getInstance().setValue("nickname",loginBean.getData().getUserInfo().getNickname());
            SpUtils.getInstance().setValue("gander",loginBean.getData().getUserInfo().getGender());
            finish();
        }else {
            MyUtil.toast(this,loginBean.getData().getMsg());
        }
    }

    @Override
    public void onBackPressed() {
        setResult(200);
        finish();
        super.onBackPressed();
    }
}

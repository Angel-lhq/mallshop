package com.example.mallshop.ui.own.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.base.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManagetActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rcl_address)
    RecyclerView rclAddress;
    @BindView(R.id.btn_new_address)
    Button btnNewAddress;

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address_manager;
    }

    @OnClick({R.id.img_back, R.id.btn_new_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_new_address:
                startActivity(new Intent(this,AddAddressActivity.class));
                break;
        }
    }
}

package com.example.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.address.base.BaseActivity;
import com.example.address.base.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {
    ImageView imgBack;
    TextView tvToolbarTitle;
    RecyclerView rclAddress;
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
        imgBack = findViewById(R.id.img_back);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        rclAddress = findViewById(R.id.rcl_address);
        btnNewAddress = findViewById(R.id.btn_new_address);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.btn_new_address) {
            startActivity(new Intent(this, AddAddressActivity.class));
        }
    }
}

package com.example.mallshop.ui.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.common.ColourLineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActiivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.colorLine)
    ColourLineView colorLine;
    @BindView(R.id.layout_coupon)
    FrameLayout layoutCoupon;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_real_price)
    TextView tvRealPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int allprice = intent.getIntExtra("allprice", 0);
        tvTotal.setText("￥ " + allprice);
        tvRealPrice.setText("实付：￥ " + allprice);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @OnClick({R.id.img_back, R.id.layout_coupon, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_coupon:
                break;
            case R.id.tv_commit:
                break;
        }
    }
}

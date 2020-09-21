package com.example.mallshop.ui.home.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.bean.BrandDetailBean;
import com.example.mallshop.bean.BrandDetailListBean;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.presenter.detail.BrandDetailPresenter;
import com.example.mallshop.ui.home.adapter.BrandDetailListAdapter;
import com.example.mallshop.utils.MyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandDetailActivity extends BaseActivity<IDetail.IDetailPresenter> implements IDetail.IDetailView, View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_cover)
    ImageView imgCover;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_brand_content)
    TextView tvBrandContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BrandDetailListAdapter brandDetailListAdapter;

    @Override
    protected IDetail.IDetailPresenter initPresenter() {
        return new BrandDetailPresenter();
    }

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        presenter.getDetail("brand/detail?id=" + id);
        presenter.getDetailList("goods/list?brandId="+id+"&page=1&size=1000");
    }

    @Override
    protected void initView() {
        imgBack.setOnClickListener(this);
        brandDetailListAdapter = new BrandDetailListAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(brandDetailListAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_brand_detail;
    }

    @Override
    public void setDetail(BrandDetailBean brandDetailBean) {
        MyUtil.loadImg(this,brandDetailBean.getData().getBrand().getList_pic_url(),imgCover);
        MyUtil.setText(tvBrandName,brandDetailBean.getData().getBrand().getName());
        MyUtil.setText(tvBrandContent,brandDetailBean.getData().getBrand().getSimple_desc());
    }

    @Override
    public void setDetailList(BrandDetailListBean brandDetailListBean) {
        brandDetailListAdapter.setData(brandDetailListBean.getData().getData());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}

package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.BrandDetailListBean;
import com.example.mallshop.utils.MyUtil;

public class BrandDetailListAdapter extends BaseAdapter {
    public BrandDetailListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_brand_detail_item;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        ImageView imgCover = (ImageView) holder.getView(R.id.img_cover);
        TextView tvName = (TextView) holder.getView(R.id.tv_good_name);
        TextView tvPrice = (TextView) holder.getView(R.id.tv_good_price);
        BrandDetailListBean.DataBeanX.DataBean bean = (BrandDetailListBean.DataBeanX.DataBean) dataBean;
        MyUtil.loadImg(context,bean.getList_pic_url(),imgCover);
        MyUtil.setText(tvName,bean.getName());
        MyUtil.setText(tvPrice,"￥"+bean.getRetail_price());

    }
}

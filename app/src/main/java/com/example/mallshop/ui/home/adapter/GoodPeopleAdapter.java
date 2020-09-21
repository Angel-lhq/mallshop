package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.GoodsDetailBean;

public class GoodPeopleAdapter extends BaseAdapter {

    public GoodPeopleAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_home_newgood;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        ImageView imgCover = (ImageView) holder.getView(R.id.img_cover);
        TextView tvName = (TextView) holder.getView(R.id.tv_new_name);
        TextView tvPrice = (TextView) holder.getView(R.id.tv_new_price);
    }
}

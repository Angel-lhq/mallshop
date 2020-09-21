package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.HomeIndexBean;

public class HomeTopicAdapter extends BaseAdapter {
    public HomeTopicAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_home_topic;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        ImageView imgCover = (ImageView) holder.getView(R.id.img_cover);
        TextView tvName = (TextView) holder.getView(R.id.tv_topic_name);
        TextView tvPrice = (TextView) holder.getView(R.id.tv_topic_price);
        TextView tvContent = (TextView) holder.getView(R.id.tv_topic_content);
        HomeIndexBean.DataBean.TopicListBean topicListBean = (HomeIndexBean.DataBean.TopicListBean) dataBean;
        if (!TextUtils.isEmpty(topicListBean.getScene_pic_url())){
            Glide.with(context).load(topicListBean.getScene_pic_url()).into(imgCover);
        }
        if (!TextUtils.isEmpty(topicListBean.getTitle())){
            tvName.setText(topicListBean.getTitle());
        }
        if (!TextUtils.isEmpty(topicListBean.getSubtitle())){
            tvContent.setText(topicListBean.getSubtitle());
        }
        tvPrice.setText("￥"+topicListBean.getPrice_info()+"元起");
    }
}

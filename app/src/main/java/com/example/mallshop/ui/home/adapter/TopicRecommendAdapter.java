package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.TopicDetailRecommendListBean;
import com.example.mallshop.utils.MyUtil;

public class TopicRecommendAdapter extends BaseAdapter {
    public TopicRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_topic_recommend;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        ImageView imgCover = (ImageView) holder.getView(R.id.img_cover);
        TextView tvContent = (TextView) holder.getView(R.id.tv_content);
        TopicDetailRecommendListBean.DataBean data = (TopicDetailRecommendListBean.DataBean) dataBean;
        MyUtil.setText(tvContent,data.getTitle());
        MyUtil.loadImg(context,data.getScene_pic_url(),imgCover);
    }
}

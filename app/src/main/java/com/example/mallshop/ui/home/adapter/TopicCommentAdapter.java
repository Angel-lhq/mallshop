package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.TopicDetailCommentBean;
import com.example.mallshop.utils.MyUtil;

public class TopicCommentAdapter extends BaseAdapter {
    public TopicCommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_topic_comment;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        ImageView imgHead = (ImageView) holder.getView(R.id.img_head);
        TextView tvName = (TextView) holder.getView(R.id.tv_name);
        TextView tvDate = (TextView) holder.getView(R.id.tv_date);
        TextView tvComment = (TextView) holder.getView(R.id.tv_comment);

        TopicDetailCommentBean.DataBeanX.DataBean data = (TopicDetailCommentBean.DataBeanX.DataBean) dataBean;
        MyUtil.setText(tvComment,data.getContent());
        MyUtil.setText(tvName,data.getUser_info().getNickname());
        MyUtil.setText(tvName,data.getAdd_time());
        MyUtil.loadImg(context,data.getUser_info().getAvatar(),imgHead);
    }
}

package com.example.mallshop.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.GoodsDetailBean;
import com.example.mallshop.utils.MyUtil;

public class GoodProblemsAdapter extends BaseAdapter {
    public GoodProblemsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_good_problem;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        TextView tvQuestion = (TextView) holder.getView(R.id.tv_question);
        TextView tvAnswer = (TextView) holder.getView(R.id.tv_answer);
        GoodsDetailBean.DataBeanX.IssueBean issueBean = (GoodsDetailBean.DataBeanX.IssueBean) dataBean;
        MyUtil.setText(tvQuestion,issueBean.getQuestion());
        MyUtil.setText(tvAnswer,issueBean.getAnswer());
    }
}

package com.example.mallshop.presenter.detail;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.TopicDetailBean;
import com.example.mallshop.bean.TopicDetailCommentBean;
import com.example.mallshop.bean.TopicDetailRecommendListBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

public class TopicDetailPresenter extends BasePresenter<IDetail.ITopicDetailView> implements IDetail.ITopicDetailPresenter {
    @Override
    public void getTopic(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getTopicDetail(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicDetailBean>(view) {
                    @Override
                    public void onNext(TopicDetailBean topicDetailBean) {
                        view.setTopic(topicDetailBean);
                    }
                }));
    }

    @Override
    public void getTopicRecommend(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getTopicRecommend(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicDetailRecommendListBean>(view) {
                    @Override
                    public void onNext(TopicDetailRecommendListBean topicDetailRecommendListBean) {
                        view.setTopicRecommendList(topicDetailRecommendListBean);
                    }
                }));
    }

    @Override
    public void getTopicComment(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getTopicComment(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicDetailCommentBean>(view) {
                    @Override
                    public void onNext(TopicDetailCommentBean topicDetailCommentBean) {
                        view.setTopicComment(topicDetailCommentBean);
                    }
                }));
    }
}

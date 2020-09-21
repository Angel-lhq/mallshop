package com.example.mallshop.constract.detail;

import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.base.IBaseView;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.BrandDetailBean;
import com.example.mallshop.bean.BrandDetailListBean;
import com.example.mallshop.bean.GoodsDetailBean;
import com.example.mallshop.bean.TopicDetailBean;
import com.example.mallshop.bean.TopicDetailCommentBean;
import com.example.mallshop.bean.TopicDetailRecommendListBean;

public interface IDetail {

    interface IDetailView extends IBaseView {
        void setDetail(BrandDetailBean brandDetailBean);
        void setDetailList(BrandDetailListBean brandDetailListBean);
    }

    interface IDetailPresenter extends IBasePresenter<IDetailView>{
        void getDetail(String url);
        void getDetailList(String url);
    }

    interface IGoodsDetailView extends IBaseView{
        void setGoods(GoodsDetailBean goods);
        void addResult(AddToCartBean addToCartBean);
    }
    interface IGoodsDetailPresenter extends IBasePresenter<IGoodsDetailView>{
        void getGoods(String url);
        void addToCart(String goodsId,int number,int projectId);
    }

    interface ITopicDetailView extends IBaseView{
        void setTopic(TopicDetailBean topic);
        void setTopicRecommendList(TopicDetailRecommendListBean topicRecommendListBean);
        void setTopicComment(TopicDetailCommentBean topicCommentBean);
    }
    interface ITopicDetailPresenter extends IBasePresenter<ITopicDetailView>{
        void getTopic(String url);
        void getTopicRecommend(String url);
        void getTopicComment(String url);
    }
}

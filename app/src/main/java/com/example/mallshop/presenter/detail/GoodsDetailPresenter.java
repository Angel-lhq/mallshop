package com.example.mallshop.presenter.detail;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.GoodsDetailBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

public class GoodsDetailPresenter extends BasePresenter<IDetail.IGoodsDetailView> implements IDetail.IGoodsDetailPresenter {
    @Override
    public void getGoods(String url) {
        addSubscribe(
                HttpUtil.getInstance()
                        .getApiService()
                        .getGoodsDetail(url)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<GoodsDetailBean>(view) {
                            @Override
                            public void onNext(GoodsDetailBean goodsDetailBean) {
                                view.setGoods(goodsDetailBean);
                            }
                        })
        );
    }

    @Override
    public void addToCart(String goodsId, int number, int projectId) {
        addSubscribe(
                HttpUtil.getInstance()
                        .getApiService()
                        .addToCart(goodsId,number,projectId)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<AddToCartBean>(view) {
                            @Override
                            public void onNext(AddToCartBean addToCartBean) {
                                view.addResult(addToCartBean);
                            }
                        }));
    }
}

package com.example.mallshop.presenter.detail;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.BrandDetailBean;
import com.example.mallshop.bean.BrandDetailListBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

public class BrandDetailPresenter extends BasePresenter<IDetail.IDetailView> implements IDetail.IDetailPresenter {
    @Override
    public void getDetail(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getBrandDetail(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandDetailBean>(view) {
                    @Override
                    public void onNext(BrandDetailBean brandDetailBean) {
                        view.setDetail(brandDetailBean);
                    }
                }));
    }

    @Override
    public void getDetailList(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getBrandDetailList(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandDetailListBean>(view) {
                    @Override
                    public void onNext(BrandDetailListBean brandDetailListBean) {
                        view.setDetailList(brandDetailListBean);
                    }
                }));
    }
}

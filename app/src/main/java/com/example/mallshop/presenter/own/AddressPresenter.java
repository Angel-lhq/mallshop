package com.example.mallshop.presenter.own;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.AddressBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

public class AddressPresenter extends BasePresenter<IOwn.IAddressView> implements IOwn.IAddressPresenter {
    @Override
    public void getAddress(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getAddress(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AddressBean>(view) {
                    @Override
                    public void onNext(AddressBean addressBean) {
                        view.setAddress(addressBean);
                    }
                }));
    }
}

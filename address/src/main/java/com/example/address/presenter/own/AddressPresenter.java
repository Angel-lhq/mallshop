package com.example.address.presenter.own;

import com.example.address.base.BasePresenter;
import com.example.address.bean.AddressBean;
import com.example.address.common.CommonSubscriber;
import com.example.address.constract.own.IOwn;
import com.example.address.utils.HttpUtil;
import com.example.address.utils.RxUtils;

public class AddressPresenter extends BasePresenter<IOwn.IAddressView> implements IOwn.IAddressPresenter {
    @Override
    public void getAddress(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getAddress(url)
                .compose(RxUtils.<AddressBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AddressBean>(view) {
                    @Override
                    public void onNext(AddressBean addressBean) {
                        view.setAddress(addressBean);
                    }
                }));
    }
}

package com.example.mallshop.presenter.own;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.RegisterBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter extends BasePresenter<IOwn.IRegisterView> implements IOwn.IRegisterPresenter {
    @Override
    public void register(Map<String, String> map) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .register(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<RegisterBean>(view) {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        view.registerResult(registerBean);
                    }
                }));
    }
}

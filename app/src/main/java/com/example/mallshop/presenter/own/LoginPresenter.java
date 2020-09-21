package com.example.mallshop.presenter.own;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.LoginBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter extends BasePresenter<IOwn.ILoginView> implements IOwn.ILoginPresenter {
    @Override
    public void login(Map<String, String> map) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .login(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<LoginBean>(view) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        view.loginResult(loginBean);
                    }
                }));
    }
}

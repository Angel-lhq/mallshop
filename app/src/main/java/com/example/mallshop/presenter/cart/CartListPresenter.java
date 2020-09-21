package com.example.mallshop.presenter.cart;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.DeleteFromCartBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.cart.ICart;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

public class CartListPresenter extends BasePresenter<ICart.ICartListView> implements ICart.ICartListPresenter {
    @Override
    public void getCartList(String url) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .getCartLit(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AddToCartBean>(view) {
                    @Override
                    public void onNext(AddToCartBean addToCartBean) {
                        view.setCartList(addToCartBean);
                    }
                }));
    }

    @Override
    public void delete(String productId) {
        addSubscribe(HttpUtil.getInstance()
                .getApiService()
                .deleteFromCart(productId)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<DeleteFromCartBean>(view) {
                    @Override
                    public void onNext(DeleteFromCartBean deleteFromCartBean) {
                        view.deleteResult(deleteFromCartBean);
                    }
                }));

    }
}

package com.example.mallshop.constract.cart;

import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.base.IBaseView;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.DeleteFromCartBean;

public interface ICart {
    interface ICartListView extends IBaseView {
        void setCartList(AddToCartBean cartList);
        void deleteResult(DeleteFromCartBean deleteFromCartBean);
    }
    interface ICartListPresenter extends IBasePresenter<ICartListView> {
        void getCartList(String url);
        void delete(String productId);
    }
    interface IDeleteCartView extends IBaseView {
    }
    interface IDeleteCartPresenter extends IBasePresenter<IDeleteCartView> {
    }
}

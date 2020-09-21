package com.example.mallshop.constract.home;

import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.base.IBaseView;
import com.example.mallshop.bean.HomeIndexBean;

import java.util.List;

public interface IHome {
    interface IHomeIndexView extends IBaseView{
        void setIndex(List<HomeIndexBean.HomeListBean> homeIndexBean);
    }
    interface IHomeIndexPresenter extends IBasePresenter<IHomeIndexView>{
        void getIndex(String url);
    }
}

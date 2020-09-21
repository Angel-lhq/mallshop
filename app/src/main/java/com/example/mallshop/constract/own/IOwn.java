package com.example.mallshop.constract.own;

import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.base.IBaseView;
import com.example.mallshop.bean.AddressBean;
import com.example.mallshop.bean.LoginBean;
import com.example.mallshop.bean.RegisterBean;

import java.util.HashMap;
import java.util.Map;

public interface IOwn {
    interface ILoginView extends IBaseView{
        void loginResult(LoginBean loginBean);
    }
    interface ILoginPresenter extends IBasePresenter<ILoginView>{
        void login(Map<String,String> map);
    }
    interface IRegisterView extends IBaseView{
        void registerResult(RegisterBean registerBean);
    }
    interface IRegisterPresenter extends IBasePresenter<IRegisterView>{
        void register(Map<String,String> map);
    }
    interface IAddressView extends IBaseView{
        void setAddress(AddressBean addressBean);
    }
    interface IAddressPresenter extends IBasePresenter<IAddressView>{
        void getAddress(String url);
    }
}

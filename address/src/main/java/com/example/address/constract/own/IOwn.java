package com.example.address.constract.own;


import com.example.address.base.IBasePresenter;
import com.example.address.base.IBaseView;
import com.example.address.bean.AddressBean;

import java.util.HashMap;
import java.util.Map;

public interface IOwn {

    interface IAddressView extends IBaseView{
        void setAddress(AddressBean addressBean);
    }
    interface IAddressPresenter extends IBasePresenter<IAddressView>{
        void getAddress(String url);
    }
}

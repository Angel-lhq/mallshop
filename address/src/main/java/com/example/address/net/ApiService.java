package com.example.address.net;

import com.example.address.bean.AddressBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    String baseUrl = "http://cdwan.cn/api/";

    @GET()
    Flowable<AddressBean> getAddress(@Url String url);

}

package com.example.mallshop.net;

import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.AddressBean;
import com.example.mallshop.bean.BrandDetailBean;
import com.example.mallshop.bean.BrandDetailListBean;
import com.example.mallshop.bean.DeleteFromCartBean;
import com.example.mallshop.bean.GoodsDetailBean;
import com.example.mallshop.bean.HomeIndexBean;
import com.example.mallshop.bean.LoginBean;
import com.example.mallshop.bean.RegisterBean;
import com.example.mallshop.bean.TopicDetailBean;
import com.example.mallshop.bean.TopicDetailCommentBean;
import com.example.mallshop.bean.TopicDetailRecommendListBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    String baseUrl = "http://cdwan.cn/api/";
//    String baseUrl = "http://169.254.96.147:8085/";
//    String baseUrl2 = "http://169.254.111.136:8085/";
//    String baseUrl3 = "http://192.168.43.237:8085/";
//    String baseUrl3 = "http://169.254.232.211:8085/";

    @GET
    Flowable<HomeIndexBean> getIndex(@Url String url);

    @GET
    Flowable<BrandDetailBean> getBrandDetail(@Url String url);

    @GET
    Flowable<BrandDetailListBean> getBrandDetailList(@Url String url);

    @GET
    Flowable<GoodsDetailBean> getGoodsDetail(@Url String url);

    @GET
    Flowable<TopicDetailBean> getTopicDetail(@Url String url);

    @GET
    Flowable<TopicDetailRecommendListBean> getTopicRecommend(@Url String url);

    @GET
    Flowable<TopicDetailCommentBean> getTopicComment(@Url String url);

    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddToCartBean> addToCart(@Field("goodsId") String goodsId,@Field("number") int number,@Field("productId") int productId);

    @POST("cart/delete")
    @FormUrlEncoded
    Flowable<DeleteFromCartBean> deleteFromCart(@Field("productId") String productId);

    @GET
    Flowable<AddToCartBean> getCartLit(@Url String url);

    @POST("auth/login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@FieldMap Map<String,String> map);

    @POST("auth/register")
    @FormUrlEncoded
    Flowable<RegisterBean> register(@FieldMap Map<String,String> map);

    @GET()
    Flowable<AddressBean> getAddress(@Url String url);

}

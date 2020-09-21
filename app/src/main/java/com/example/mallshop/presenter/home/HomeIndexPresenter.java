package com.example.mallshop.presenter.home;

import com.example.mallshop.base.BasePresenter;
import com.example.mallshop.bean.HomeIndexBean;
import com.example.mallshop.common.CommonSubscriber;
import com.example.mallshop.constract.home.IHome;
import com.example.mallshop.utils.HttpUtil;
import com.example.mallshop.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.functions.Function;

public class HomeIndexPresenter extends BasePresenter<IHome.IHomeIndexView> implements IHome.IHomeIndexPresenter {
    @Override
    public void getIndex(String url) {
        addSubscribe(
                HttpUtil.getInstance()
                        .getApiService()
                        .getIndex(url)
                        .compose(RxUtils.rxScheduler())
                        //数据加工 把HomeBean转换成List<HomeListBean>
                        .map(new Function<HomeIndexBean, List<HomeIndexBean.HomeListBean>>() {
                            @Override
                            public List<HomeIndexBean.HomeListBean> apply(HomeIndexBean homeBean) throws Exception {
                                List<HomeIndexBean.HomeListBean> list = new ArrayList<>();
                                //第一个对象的封装 Banner
                                HomeIndexBean.HomeListBean banner = new HomeIndexBean.HomeListBean();
                                banner.currentType = HomeIndexBean.ITEM_TYPE_BANNER;
                                banner.data = homeBean.getData().getBanner();
                                list.add(banner);
                                //导航的封装
                                HomeIndexBean.HomeListBean tab = new HomeIndexBean.HomeListBean();
                                tab.currentType = HomeIndexBean.ITEM_TYPE_TAB;
                                tab.data = homeBean.getData().getChannel();
                                list.add(tab);
                                //封装带top边距的标题
                                HomeIndexBean.HomeListBean title1 = new HomeIndexBean.HomeListBean();
                                title1.currentType = HomeIndexBean.ITEM_TYPE_TITLETOP;
                                title1.data = "品牌制造商直供";
                                list.add(title1);
                                //封装品牌制造商直供的列表数据
                                for (int i=0; i<homeBean.getData().getBrandList().size(); i++){
                                    HomeIndexBean.HomeListBean brand = new HomeIndexBean.HomeListBean();
                                    brand.currentType = HomeIndexBean.ITEM_TYPE_BRAND;
                                    brand.data = homeBean.getData().getBrandList().get(i);
                                    list.add(brand);
                                }
                                //新品首发标题
                                HomeIndexBean.HomeListBean title2 = new HomeIndexBean.HomeListBean();
                                title2.currentType = HomeIndexBean.ITEM_TYPE_TITLE;
                                title2.data = "周一周四·新品首发";
                                list.add(title2);
                                //新品首发数据封装
                                for (int i=0; i<homeBean.getData().getNewGoodsList().size(); i++){
                                    HomeIndexBean.HomeListBean brand = new HomeIndexBean.HomeListBean();
                                    brand.currentType = HomeIndexBean.ITEM_TYPE_NEW;
                                    brand.data = homeBean.getData().getNewGoodsList().get(i);
                                    list.add(brand);
                                }
                                //人气推荐
                                HomeIndexBean.HomeListBean title3 = new HomeIndexBean.HomeListBean();
                                title3.currentType = HomeIndexBean.ITEM_TYPE_TITLETOP;
                                title3.data = "人气推荐";
                                list.add(title3);
                                //人气推荐数据
                                for (int i=0; i<homeBean.getData().getHotGoodsList().size(); i++){
                                    HomeIndexBean.HomeListBean brand = new HomeIndexBean.HomeListBean();
                                    brand.currentType = HomeIndexBean.ITEM_TYPE_HOT;
                                    brand.data = homeBean.getData().getHotGoodsList().get(i);
                                    list.add(brand);
                                }
                                //专题精选
                                HomeIndexBean.HomeListBean title4 = new HomeIndexBean.HomeListBean();
                                title4.currentType = HomeIndexBean.ITEM_TYPE_TITLETOP;
                                title4.data = "专题精选";
                                list.add(title4);
                                //人气推荐数据
                                HomeIndexBean.HomeListBean brand = new HomeIndexBean.HomeListBean();
                                brand.currentType = HomeIndexBean.ITEM_TYPE_TOPIC;
                                brand.data = homeBean.getData().getTopicList();
                                list.add(brand);
                                /*for (int i=0; i<homeBean.getData().getTopicList().size(); i++){
                                    HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                                    brand.currentType = HomeBean.ITEM_TYPE_TOPIC;
                                    brand.data = homeBean.getData().getTopicList().get(i);
                                    list.add(brand);
                                }*/
                                for (HomeIndexBean.DataBean.CategoryListBean categoryListBean : homeBean.getData().getCategoryList()){
                                    HomeIndexBean.HomeListBean title = new HomeIndexBean.HomeListBean();
                                    title.currentType = HomeIndexBean.ITEM_TYPE_TITLETOP;
                                    title.data = categoryListBean.getName();
                                    list.add(title);
                                    for (HomeIndexBean.DataBean.CategoryListBean.GoodsListBean good : categoryListBean.getGoodsList()){
                                        HomeIndexBean.HomeListBean goodList = new HomeIndexBean.HomeListBean();
                                        goodList.currentType = HomeIndexBean.ITEM_TYPE_CATEGORY;
                                        goodList.data = good;
                                        list.add(goodList);
                                    }
                                }
                                return list;
                            }
                        })
                        .subscribeWith(new CommonSubscriber<List<HomeIndexBean.HomeListBean>>(view) {
                            @Override
                            public void onNext(List<HomeIndexBean.HomeListBean> homeIndexBean) {
                                view.setIndex(homeIndexBean);
                            }
                        })
        );
    }
}

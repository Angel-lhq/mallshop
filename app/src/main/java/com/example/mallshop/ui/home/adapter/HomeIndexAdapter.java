package com.example.mallshop.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mallshop.MainActivity;
import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.HomeIndexBean;
import com.example.mallshop.ui.home.activities.TopicDetailActivity;
import com.example.mallshop.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeIndexAdapter extends BaseMultiItemQuickAdapter<HomeIndexBean.HomeListBean, BaseViewHolder> {
    private Context context;
    private HomeTopicAdapter homeTopicAdapter;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeIndexAdapter(List<HomeIndexBean.HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(HomeIndexBean.ITEM_TYPE_BANNER, R.layout.layout_home_banner);
        addItemType(HomeIndexBean.ITEM_TYPE_TAB,R.layout.layout_home_tab);
        addItemType(HomeIndexBean.ITEM_TYPE_TITLETOP,R.layout.layout_title_top);
        addItemType(HomeIndexBean.ITEM_TYPE_BRAND,R.layout.layout_home_brand);
        addItemType(HomeIndexBean.ITEM_TYPE_TITLE,R.layout.layout_title);
        addItemType(HomeIndexBean.ITEM_TYPE_NEW,R.layout.layout_home_newgood);
        addItemType(HomeIndexBean.ITEM_TYPE_HOT,R.layout.layout_home_hot);
        addItemType(HomeIndexBean.ITEM_TYPE_TOPIC,R.layout.layout_home_topiclist);
        addItemType(HomeIndexBean.ITEM_TYPE_CATEGORY,R.layout.layout_home_category);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeIndexBean.HomeListBean item) {
        switch (item.getItemType()){
            case HomeIndexBean.ITEM_TYPE_TITLE:
                updateTitle(helper, (String) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_TITLETOP:
                updateTitle(helper, (String) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_BANNER:
                updateBanner(helper, (List<HomeIndexBean.DataBean.BannerBean>) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_TAB:
                updateTab(helper, (List<HomeIndexBean.DataBean.ChannelBean>) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_BRAND:
                updateBrand(helper, (HomeIndexBean.DataBean.BrandListBean) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_NEW:
                updateNewGood(helper, (HomeIndexBean.DataBean.NewGoodsListBean) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_HOT:
                udpateHot(helper, (HomeIndexBean.DataBean.HotGoodsListBean) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_TOPIC:
                updateTopic(helper, (List<HomeIndexBean.DataBean.TopicListBean>) item.data);
                break;
            case HomeIndexBean.ITEM_TYPE_CATEGORY:
                updateCategory(helper, (HomeIndexBean.DataBean.CategoryListBean.GoodsListBean) item.data);
                break;
        }
    }

    /**
     * 刷新分类商品 category
     * @param holder
     * @param data
     */
    private void updateCategory(BaseViewHolder holder, HomeIndexBean.DataBean.CategoryListBean.GoodsListBean data) {
        if (!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView)holder.getView(R.id.img_cover));
        }
        if (!TextUtils.isEmpty(data.getName())){
            ((TextView)holder.getView(R.id.tv_category_name)).setText(data.getName());
        }
        ((TextView)holder.getView(R.id.tv_category_price)).setText("￥"+(double)data.getRetail_price());
    }

    /**
     * 刷新专题
     * @param holder
     * @param data
     */
    private void updateTopic(BaseViewHolder holder, List<HomeIndexBean.DataBean.TopicListBean> data) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (homeTopicAdapter == null) {
            homeTopicAdapter = new HomeTopicAdapter(context);
            recyclerView.setAdapter(homeTopicAdapter);
        }else if (recyclerView.getAdapter() == null){
            recyclerView.setAdapter(homeTopicAdapter);
        }
        homeTopicAdapter.setData(data);
        homeTopicAdapter.setiOnClick(new BaseAdapter.IOnClick() {
            @Override
            public void onClick(int position, Object o, View view) {
                Intent intent = new Intent(context,TopicDetailActivity.class);
                intent.putExtra("id",((HomeIndexBean.DataBean.TopicListBean) o).getId());
                context.startActivity(intent);
            }
        });
    }

    /**
     * 刷新人气推荐 hot
     * @param holder
     * @param data
     */
    private void udpateHot(BaseViewHolder holder, HomeIndexBean.DataBean.HotGoodsListBean data) {
        if (!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView)holder.getView(R.id.img_cover));
        }
        holder.setText(R.id.tv_hot_name,data.getName());
        holder.setText(R.id.tv_hot_content,data.getGoods_brief());
        holder.setText(R.id.tv_hot_price,"￥" + (double)data.getRetail_price());
    }

    /**
     * 刷新新品首发 newgoods
     * @param holder
     * @param data
     */
    private void updateNewGood(BaseViewHolder holder, HomeIndexBean.DataBean.NewGoodsListBean data) {
        if (!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView)holder.getView(R.id.img_cover));
        }
        holder.setText(R.id.tv_new_name,data.getName());
        holder.setText(R.id.tv_new_price,"￥"+(double)data.getRetail_price());
    }

    /**
     * 刷新品牌直供 brand
     * @param holder
     * @param data
     */
    private void updateBrand(BaseViewHolder holder, HomeIndexBean.DataBean.BrandListBean data) {
        if (!TextUtils.isEmpty(data.getNew_pic_url())){
            Glide.with(context).load(data.getNew_pic_url()).into((ImageView)holder.getView(R.id.img_cover));
        }
        holder.setText(R.id.tv_brand_name,data.getName());
        holder.setText(R.id.tv_brand_price,data.getFloor_price()+"元起");
    }

    /**
     * 刷新title
     * @param holder
     * @param data
     */
    private void updateTitle(BaseViewHolder holder, String data) {
        ((TextView)holder.getView(R.id.tv_title)).setText(data);
    }
    /**
     * 刷新channel
     * @param holder
     * @param data
     */
    private void updateTab(BaseViewHolder holder, List<HomeIndexBean.DataBean.ChannelBean> data) {
        LinearLayout layoutTab = holder.getView(R.id.layout_tab);
        if (layoutTab.getChildCount() == 0){
            for (HomeIndexBean.DataBean.ChannelBean channelBean : data) {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                openBidevent(channelBean.getIcon_url(),textView,channelBean.getName());
                textView.setLayoutParams(params);
                layoutTab.addView(textView);
            }
        }
    }

    //网络加载成功更改tv drawable图片：
    private void openBidevent(String icon_url, final TextView textView, String name) {
        Glide.with(context).asBitmap().load(icon_url).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                BitmapDrawable drawable= new BitmapDrawable(context.getResources(), resource);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                textView.setCompoundDrawables(null,drawable,null,null);
            }
        });
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(SystemUtils.dp2px(context,7));
        textView.setText(name);
    }

    /**
     * 刷新banner
     * @param holder
     * @param data
     */
    private void updateBanner(BaseViewHolder holder, List<HomeIndexBean.DataBean.BannerBean> data) {
        Banner banner = holder.getView(R.id.banner);
        List<String> imgs = new ArrayList<>();
        for (HomeIndexBean.DataBean.BannerBean bannerBean : data){
            imgs.add(bannerBean.getImage_url());
        }
        if (banner != null && banner.getTag() == null){
            banner.setAdapter(new BannerImageAdapter<String>(imgs) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    //图片加载自己实现
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }
            }).addBannerLifecycleObserver((MainActivity)context)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(context))
                    .setBannerRound(20)
                    .setBannerGalleryEffect(5,5,10);
            banner.setTag("true");
        }
    }
}

package com.example.mallshop.ui.home;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mallshop.R;
import com.example.mallshop.base.BaseFragment;
import com.example.mallshop.bean.HomeIndexBean;
import com.example.mallshop.constract.home.IHome;
import com.example.mallshop.presenter.home.HomeIndexPresenter;
import com.example.mallshop.ui.home.activities.BrandDetailActivity;
import com.example.mallshop.ui.home.activities.GoodsDetailActivity;
import com.example.mallshop.ui.home.adapter.HomeIndexAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<IHome.IHomeIndexPresenter> implements IHome.IHomeIndexView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<HomeIndexBean.HomeListBean> list;
    private HomeIndexAdapter homeIndexAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IHome.IHomeIndexPresenter initPresenter() {
        return new HomeIndexPresenter();
    }

    @Override
    protected void initData() {
        presneter.getIndex("index");
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        homeIndexAdapter = new HomeIndexAdapter(list,context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        homeIndexAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currentType;
                switch (type){
                    case HomeIndexBean.ITEM_TYPE_BANNER:
                    case HomeIndexBean.ITEM_TYPE_TAB:
                    case HomeIndexBean.ITEM_TYPE_TITLE:
                    case HomeIndexBean.ITEM_TYPE_TITLETOP:
                    case HomeIndexBean.ITEM_TYPE_HOT:
                    case HomeIndexBean.ITEM_TYPE_TOPIC:
                        return 2;
                    case HomeIndexBean.ITEM_TYPE_BRAND:
                    case HomeIndexBean.ITEM_TYPE_NEW:
                    case HomeIndexBean.ITEM_TYPE_CATEGORY:
                        return 1;
                }
                return 0;

            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        homeIndexAdapter.bindToRecyclerView(recyclerView);
        homeIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int currentType = list.get(position).currentType;
                Intent intent = new Intent();
                switch (currentType) {
                    case HomeIndexBean.ITEM_TYPE_BANNER:
                        break;
                    case HomeIndexBean.ITEM_TYPE_TAB:
                        break;
                    case HomeIndexBean.ITEM_TYPE_TITLE:
                        break;
                    case HomeIndexBean.ITEM_TYPE_TITLETOP:
                        break;
                    case HomeIndexBean.ITEM_TYPE_BRAND:
                        HomeIndexBean.DataBean.BrandListBean brandListBean = (HomeIndexBean.DataBean.BrandListBean) list.get(position).data;
                        intent.putExtra("id", brandListBean.getId());
                        intent.setClass(context, BrandDetailActivity.class);
                        context.startActivity(intent);
                        break;
                    case HomeIndexBean.ITEM_TYPE_HOT:
                        HomeIndexBean.DataBean.HotGoodsListBean hotGoodsListBean = (HomeIndexBean.DataBean.HotGoodsListBean) list.get(position).data;
                        intent.putExtra("id",hotGoodsListBean.getId());
                        intent.setClass(context, GoodsDetailActivity.class);
                        startActivityForResult(intent,1000);
                        break;
                    case HomeIndexBean.ITEM_TYPE_CATEGORY:
                        HomeIndexBean.DataBean.CategoryListBean.GoodsListBean goodsListBean = (HomeIndexBean.DataBean.CategoryListBean.GoodsListBean) list.get(position).data;
                        intent.putExtra("id",goodsListBean.getId());
                        intent.setClass(context, GoodsDetailActivity.class);
                        startActivityForResult(intent,1000);
                        break;
                    case HomeIndexBean.ITEM_TYPE_NEW:
                        HomeIndexBean.DataBean.NewGoodsListBean newGoodsListBean = (HomeIndexBean.DataBean.NewGoodsListBean) list.get(position).data;
                        intent.putExtra("id",newGoodsListBean.getId());
                        intent.setClass(context, GoodsDetailActivity.class);
                        startActivityForResult(intent,1000);
                        break;
                    case HomeIndexBean.ITEM_TYPE_TOPIC:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setIndex(List<HomeIndexBean.HomeListBean> homeIndexBean) {
        list.addAll(homeIndexBean);
        homeIndexAdapter.notifyDataSetChanged();
    }
}
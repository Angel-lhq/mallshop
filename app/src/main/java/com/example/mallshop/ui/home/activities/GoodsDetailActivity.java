package com.example.mallshop.ui.home.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.GoodsDetailBean;
import com.example.mallshop.common.CartCustomView;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.presenter.detail.GoodsDetailPresenter;
import com.example.mallshop.ui.home.adapter.GoodPeopleAdapter;
import com.example.mallshop.ui.home.adapter.GoodProblemsAdapter;
import com.example.mallshop.ui.own.activities.LoginActivity;
import com.example.mallshop.utils.MyUtil;
import com.example.mallshop.utils.SpUtils;
import com.example.mallshop.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity<IDetail.IGoodsDetailPresenter> implements IDetail.IGoodsDetailView, View.OnClickListener, CartCustomView.IClick {
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_des)
    TextView txtDes;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_product)
    TextView txtProduct;
    @BindView(R.id.layout_norms)
    FrameLayout layoutNorms;
    @BindView(R.id.rel_comment)
    RelativeLayout layoutComment;
    @BindView(R.id.layout_imgs)
    LinearLayout layoutImgs;
    @BindView(R.id.layout_parameter)
    LinearLayout layoutParameter;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.rcl_people)
    RecyclerView rclPeople;
    @BindView(R.id.rcl_problem)
    RecyclerView rclProblem;
    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.txt_buy)
    TextView txtBuy;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.tv_add_cart)
    TextView tvAddCart;
    @BindView(R.id.tv_count)
    TextView tvCount;

    private String html = "<html>\n" +
            "            <head>" +
            "                        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
            "                        <style>*{margin:0;padding:0;}img{max-width: 100%; width:auto; height:auto;}</style>" +
            "            </head>" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private GoodProblemsAdapter goodProblemsAdapter;
    private GoodPeopleAdapter goodPeopleAdapter;
    private PopupWindow popupWindow;
    private GoodsDetailBean.DataBeanX goodsData;
    private ImageView imgGood;
    private TextView tvPrice;
    private TextView tvSpecifications;
    private int count = 1;
    private TextView tvCountPop;

    @Override
    protected IDetail.IGoodsDetailPresenter initPresenter() {
        return new GoodsDetailPresenter();
    }
    private void initPop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_pop_photo, null, false);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        imgGood = inflate.findViewById(R.id.img_good);
        TextView txtClose = inflate.findViewById(R.id.txt_close);
        tvPrice = inflate.findViewById(R.id.tv_price);
        tvSpecifications = inflate.findViewById(R.id.tv_specifications);
        CartCustomView layoutCartPop = inflate.findViewById(R.id.layout_cart_pop);
        RelativeLayout layoutCollect = inflate.findViewById(R.id.layout_collect);
        TextView txtBuy = inflate.findViewById(R.id.txt_buy);
        TextView tvAddCart = inflate.findViewById(R.id.tv_add_cart);
        tvCountPop = inflate.findViewById(R.id.tv_count);
        RelativeLayout layoutCart = inflate.findViewById(R.id.layout_cart);
        txtBuy.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);
        layoutCollect.setOnClickListener(this);
        layoutCart.setOnClickListener(this);
        txtClose.setOnClickListener(this);
        layoutCartPop.initView();
        layoutCartPop.setOnIClickListener(this);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
    }
    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        presenter.getGoods("goods/detail?id=" + id);
    }

    @Override
    protected void initView() {
        initPop();
        goodProblemsAdapter = new GoodProblemsAdapter(this);
        rclProblem.setLayoutManager(new LinearLayoutManager(this));
        rclProblem.setAdapter(goodProblemsAdapter);
        goodPeopleAdapter = new GoodPeopleAdapter(this);
        rclPeople.setLayoutManager(new GridLayoutManager(this,2));
        rclPeople.setAdapter(goodPeopleAdapter);
        layoutNorms.setOnClickListener(this);
        layoutCollect.setOnClickListener(this);
        layoutCart.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_detail;
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setGoods(GoodsDetailBean goods) {
        goodsData = goods.getData();
        updateBanner(goodsData.getGallery());
        updateInfo(goodsData.getInfo());
        updateBrand(goodsData.getBrand());
        GoodsDetailBean.DataBeanX.CommentBean data = goodsData.getComment();
        if (data.getCount() == 0) {
            layoutComment.setVisibility(View.GONE);
        } else {
            updateComment(data);
        }
        updateProblems(goodsData.getIssue());
        if (goodsData.getAttribute() != null){
            updateParameter(goodsData.getAttribute());
        }
    }

    /**
     * 刷新商品参数 parameter
     * @param attribute
     */
    private void updateParameter(List<GoodsDetailBean.DataBeanX.AttributeBean> attribute) {
        for (GoodsDetailBean.DataBeanX.AttributeBean attributeBean : attribute){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_parameter_item, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            view.setLayoutParams(params);
            TextView name = view.findViewById(R.id.tv_para_name);
            TextView value = view.findViewById(R.id.tv_para_value);
            name.setText(attributeBean.getName());
            value.setText(attributeBean.getValue());
            layoutParameter.addView(view);
        }
    }

    @Override
    public void addResult(AddToCartBean addToCartBean) {
        if (addToCartBean.getData() != null){
            int goodsCount = addToCartBean.getData().getCartTotal().getGoodsCount();
            tvCount.setText(goodsCount+"");
            tvCountPop.setText(goodsCount+"");
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        }else {
            MyUtil.toast(this,addToCartBean.getErrmsg());
        }
    }

    /**
     * 常见问题
     * @param issue
     */
    private void updateProblems(List<GoodsDetailBean.DataBeanX.IssueBean> issue) {
        goodProblemsAdapter.setData(issue);
    }

    /**
     * 评论
     * @param data
     */
    private void updateComment(GoodsDetailBean.DataBeanX.CommentBean data) {
        MyUtil.setText(tvName,data.getData().getNickname());
        MyUtil.setText(tvComment,data.getData().getContent());
        MyUtil.setText(tvDate,data.getData().getAdd_time());
        for (GoodsDetailBean.DataBeanX.CommentBean.DataBean.PicListBean picListBean : data.getData().getPic_list()) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
            layoutParams.bottomMargin = 10;
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.topMargin = 10;
            imageView.setLayoutParams(layoutParams);
            Glide.with(this).load(picListBean.getPic_url()).into(imageView);
            layoutImgs.addView(imageView);
        }
    }

    /**
     * 品牌
     * @param brand
     */
    private void updateBrand(GoodsDetailBean.DataBeanX.BrandBean brand) {
        MyUtil.setText(txtProduct, brand.getName());
    }

    /**
     * 商品信息
     * @param info
     */
    private void updateInfo(GoodsDetailBean.DataBeanX.InfoBean info) {
        MyUtil.setText(txtName, info.getName());
        MyUtil.setText(txtDes, info.getGoods_brief());
        MyUtil.setText(txtPrice, "￥" + info.getRetail_price());
        if (!TextUtils.isEmpty(info.getGoods_desc())){
            html = html.replace("$",info.getGoods_desc());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(html, "text/html; charset=utf-8", "utf-8");
        }
    }

    /**
     * 轮播图
     * @param gallery
     */
    private void updateBanner(List<GoodsDetailBean.DataBeanX.GalleryBean> gallery) {
        List<String> imgs = new ArrayList<>();
        for (GoodsDetailBean.DataBeanX.GalleryBean item : gallery) {
            imgs.add(item.getImg_url());
        }
        if (banner != null && banner.getTag() == null) {
            banner.setAdapter(new BannerImageAdapter<String>(imgs) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    //图片加载自己实现
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }
            }).addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(this))
                    .setBannerRound(20)
                    .setBannerGalleryEffect(5, 5, 10);
            banner.setTag("true");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击选择数量规格 弹窗
            case R.id.layout_norms:
                showPopupwindow();
                break;
            case R.id.txt_close:
                popupWindow.dismiss();
                break;
            case R.id.txt_buy:

                break;
            case R.id.tv_add_cart:
                String token = SpUtils.getInstance().getString("token");
                if (!TextUtils.isEmpty(token)){
                    if (popupWindow != null && popupWindow.isShowing()){
                        if (goodsData.getProductList().size() > 0){
                            presenter.addToCart(goodsData.getProductList().get(0).getGoods_id(),
                                    count,goodsData.getProductList().get(0).getId());
                        }
                    }else {
                        showPopupwindow();
                    }
                }else {
                    startActivityForResult(new Intent(this, LoginActivity.class),100);
                }
                break;
            case R.id.layout_collect:
                break;
            case R.id.layout_cart:
                String token1 = SpUtils.getInstance().getString("token");
                if (!TextUtils.isEmpty(token1)){
                    setResult(2000);
                    finish();
                }else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    private void showPopupwindow() {
        if (goodsData != null){
            if (goodsData.getGallery().size() > 0)
            MyUtil.loadImg(this,goodsData.getGallery().get(0).getImg_url(),imgGood);
            MyUtil.setText(tvPrice,"价格：￥"+goodsData.getInfo().getRetail_price());
        }
        //popupWindow弹出时背景变暗 消失后背景还原
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;//FLAG_DIM_BEHND
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAtLocation(nestedScrollView, Gravity.BOTTOM,0,0);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha=0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @Override
    public void clickCB(int value) {
        count = value;
    }
}

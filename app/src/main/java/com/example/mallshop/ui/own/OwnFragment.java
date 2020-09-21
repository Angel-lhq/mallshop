package com.example.mallshop.ui.own;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseFragment;
import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.ui.own.activities.AddressManagetActivity;
import com.example.mallshop.ui.own.activities.LoginActivity;
import com.example.mallshop.utils.MyUtil;
import com.example.mallshop.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class OwnFragment extends BaseFragment {
    @BindView(R.id.btn_quit)
    Button btnQuit;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_into)
    ImageView imgInto;
    @BindView(R.id.layout_head)
    ConstraintLayout layoutHead;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_tracks)
    TextView tvTracks;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_securty)
    TextView tvSecurty;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_own;
    }

    @OnClick(R.id.btn_quit)
    public void onViewClicked() {
        SpUtils.getInstance().clear();
    }

    @OnClick({R.id.img_head, R.id.tv_name, R.id.img_into, R.id.tv_order, R.id.tv_love, R.id.tv_location, R.id.tv_help, R.id.tv_coupon, R.id.tv_tracks, R.id.tv_vip, R.id.tv_securty, R.id.tv_card, R.id.tv_service, R.id.tv_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head://头像
                break;
            case R.id.tv_name://昵称 | 登录
            case R.id.img_into://详情 | 登录
                String token = SpUtils.getInstance().getString("token");
                if (TextUtils.isEmpty(token)){
                    //未登录  调至登录界面
                    startActivity(new Intent(context, LoginActivity.class));
                }else {
                    //已登录
                }
                break;
            case R.id.tv_order://我的订单
                break;
            case R.id.tv_love://我的收藏
                break;
            case R.id.tv_location://地址管理
                startActivity(new Intent(context, AddressManagetActivity.class));
                break;
            case R.id.tv_help://帮助中心
                break;
            case R.id.tv_coupon://优惠卷
                break;
            case R.id.tv_tracks://我的足迹
                break;
            case R.id.tv_vip://会员福利
                break;
            case R.id.tv_securty://账号安全
                break;
            case R.id.tv_card://礼品卡
                break;
            case R.id.tv_service://联系客服
                break;
            case R.id.tv_feedback://意见反馈
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            String nickname = SpUtils.getInstance().getString("nickname");
            String username = SpUtils.getInstance().getString("username");
            String avatar = SpUtils.getInstance().getString("avatar");
            MyUtil.setText(tvName,username);
            MyUtil.setText(tvName,nickname);
            MyUtil.loadImg(context,avatar,imgHead);
        }
    }
}

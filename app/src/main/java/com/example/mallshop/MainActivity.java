package com.example.mallshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.ui.classify.ClassifyFragment;
import com.example.mallshop.ui.home.HomeFragment;
import com.example.mallshop.ui.own.OwnFragment;
import com.example.mallshop.ui.own.activities.LoginActivity;
import com.example.mallshop.ui.shoppingcart.ShoppingFragment;
import com.example.mallshop.ui.special.SpecialFragment;
import com.example.mallshop.utils.SpUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    ConstraintLayout toolbar;

    private List<Fragment> fragments;
    private String[] titles = {"首页","专题","分类","购物车","我的"};
    private int[] icons = {R.drawable.se_home,R.drawable.se_special,R.drawable.se_classify,R.drawable.se_shopping,R.drawable.se_own};

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.GONE);
        HomeFragment homeFragment = new HomeFragment();
        SpecialFragment specialFragment = new SpecialFragment();
        ClassifyFragment classifyFragment = new ClassifyFragment();
        ShoppingFragment shoppingFragment = new ShoppingFragment();
        OwnFragment ownFragment = new OwnFragment();
        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(specialFragment);
        fragments.add(classifyFragment);
        fragments.add(shoppingFragment);
        fragments.add(ownFragment);
        viewPager.setAdapter(new VpAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(getView(titles[i],icons[i]));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbar.setVisibility(View.VISIBLE);
                switch (tab.getPosition()){
                    case 3:
                        String token = SpUtils.getInstance().getString("token");
                        if (TextUtils.isEmpty(token)){
                            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class),100);
                        }else {
                            viewPager.setCurrentItem(3);
                        }
                        break;
                    case 4:
                        toolbar.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View getView(String title, int icon) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab);
        ImageView imgTab = view.findViewById(R.id.img_tab);
        tvTab.setText(title);
        imgTab.setImageResource(icon);
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    public class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000){
            tabLayout.getTabAt(3).select();
        }
        if (resultCode == 200){
            tabLayout.getTabAt(0).select();
        }
    }
}
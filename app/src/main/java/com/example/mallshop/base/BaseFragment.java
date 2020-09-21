package com.example.mallshop.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView{

    protected P presneter;
    private Unbinder unbinder;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        presneter = initPresenter();
        initView(getView());
        if (presneter != null){
            presneter.attachView(this);
        }
        initData();
    }

    protected abstract P initPresenter();

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayout();

    @Override
    public void showLoading(int visible) {

    }

    @Override
    public void showTips(String tips) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
        if (presneter != null){
            presneter.detachView();
        }
    }
}

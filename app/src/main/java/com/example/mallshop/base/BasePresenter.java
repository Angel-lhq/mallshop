package com.example.mallshop.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    public WeakReference<V> weakReference;
    protected V view;
    public CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V v) {
        weakReference = new WeakReference<>(v);
        view = weakReference.get();
    }

    @Override
    public void detachView() {
        view = null;
    }

    /**
     * 把请求网络的数据对象添加到排队中
     * @param disposable
     */
    public void addSubscribe(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 清除排列的请求对象
     * @param disposable
     */
    public void clearSubscribe(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable.clear();
        }
    }

}

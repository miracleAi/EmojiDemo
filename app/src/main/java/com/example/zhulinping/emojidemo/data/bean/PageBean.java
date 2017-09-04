package com.example.zhulinping.emojidemo.data.bean;

import android.view.View;
import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.interfaces.PageViewInstantiateListener;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class PageBean<T extends PageBean>{
    //显示一页表情的view
    protected View mRootView;

    protected PageViewInstantiateListener mPageViewInstantiateListener;

    public void setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) { this.mPageViewInstantiateListener = pageViewInstantiateListener; }

    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    public PageBean(){ }

    public PageBean(View view){
        this.mRootView = view;
    }
    //为了可以加在不同样式的pageview，直接传入或者动态通过listener调用添加
    public View instantiateItem(ViewGroup container, int position, T pageEntity) {
        if(mPageViewInstantiateListener != null){
            return mPageViewInstantiateListener.instantiateItem(container, position, this);
        }
        return getRootView();
    }
}

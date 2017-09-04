package com.example.zhulinping.emojidemo.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.data.bean.PageBean;

/**
 * Created by zhulinping on 2017/9/4.
 */

public interface PageViewInstantiateListener<T extends PageBean> {
    View instantiateItem(ViewGroup container, int position, T pageEntity);
}

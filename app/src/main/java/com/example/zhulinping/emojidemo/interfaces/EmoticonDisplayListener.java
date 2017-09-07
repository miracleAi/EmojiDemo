package com.example.zhulinping.emojidemo.interfaces;

import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.adapter.EmoticonsAdapter;

/**
 * Created by zhulinping on 2017/9/6.
 */

public interface EmoticonDisplayListener<T> {
    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}

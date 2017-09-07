package com.example.zhulinping.emojidemo.interfaces;

/**
 * Created by zhulinping on 2017/9/6.
 */

public interface EmoticonClickListener<T> {
    void onEmoticonClick(T t, int actionType, boolean isDelBtn);
}

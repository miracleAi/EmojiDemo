package com.example.zhulinping.emojidemo.emohiview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.zhulinping.emojidemo.utils.EmojiKeyboardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class FuncLayout extends LinearLayout {
    public final int DEF_KEY = Integer.MIN_VALUE;

    private final SparseArray<View> mFuncViewArrayMap = new SparseArray<>();

    private int mCurrentFuncKey = DEF_KEY;

    protected int mHeight = 0;

    public FuncLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public void addFuncView(int key, View view) {
        if (mFuncViewArrayMap.get(key) != null) {
            return;
        }
        mFuncViewArrayMap.put(key, view);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setVisibility(GONE);
    }
    //表情键盘与软键盘切换，或者应用键盘与软件盘切换
    public void toggleFuncView(int key, boolean isSoftKeyboardPop, EditText editText) {
        //当前view是需要切换的view--表情或应用
        if (getCurrentFuncKey() == key) {
            if (isSoftKeyboardPop) {
                if (EmojiKeyboardUtils.isFullScreen((Activity) getContext())) {
                    EmojiKeyboardUtils.closeSoftKeyboard(editText);
                } else {
                    EmojiKeyboardUtils.closeSoftKeyboard(getContext());
                }
            } else {
                EmojiKeyboardUtils.openSoftKeyboard(editText);
            }
        } else {
            if (isSoftKeyboardPop) {
                if (EmojiKeyboardUtils.isFullScreen((Activity) getContext())) {
                    EmojiKeyboardUtils.closeSoftKeyboard(editText);
                } else {
                    EmojiKeyboardUtils.closeSoftKeyboard(getContext());
                }
            }
            //如果不是，则切换功能布局显示view（只是切换view，可能隐藏在软键盘之后）
            showFuncView(key);
        }
    }

    public void showFuncView(int key) {
        if (mFuncViewArrayMap.get(key) == null) {
            return;
        }
        for (int i = 0; i < mFuncViewArrayMap.size(); i++) {
            int keyTemp = mFuncViewArrayMap.keyAt(i);
            if (keyTemp == key) {
                mFuncViewArrayMap.get(keyTemp).setVisibility(VISIBLE);
            } else {
                mFuncViewArrayMap.get(keyTemp).setVisibility(GONE);
            }
        }
        mCurrentFuncKey = key;
        setVisibility(true);

        if (onFuncChangeListener != null) {
            onFuncChangeListener.onFuncChange(mCurrentFuncKey);
        }
    }

    public void hideAllFuncView() {
        for (int i = 0; i < mFuncViewArrayMap.size(); i++) {
            int keyTemp = mFuncViewArrayMap.keyAt(i);
            mFuncViewArrayMap.get(keyTemp).setVisibility(GONE);
        }
        mCurrentFuncKey = DEF_KEY;
        setVisibility(false);
    }

    public int getCurrentFuncKey() {
        return mCurrentFuncKey;
    }

    public void updateHeight(int height) {
        this.mHeight = height;
    }

    public boolean isOnlyShowSoftKeyboard() {
        return mCurrentFuncKey == DEF_KEY;
    }
    //设置功能键盘显示或隐藏
    public void setVisibility(boolean b) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        if (b) {
            setVisibility(VISIBLE);
            params.height = mHeight;
            if (mListenerList != null) {
                for (OnFuncKeyBoardListener l : mListenerList) {
                    l.onFuncPop(mHeight);
                }
            }
        } else {
            setVisibility(GONE);
            params.height = 0;
            if (mListenerList != null) {
                for (OnFuncKeyBoardListener l : mListenerList) {
                    l.onFuncClose();
                }
            }
        }
        setLayoutParams(params);
    }

    private List<OnFuncKeyBoardListener> mListenerList;

    public void addOnKeyBoardListener(OnFuncKeyBoardListener l) {
        if (mListenerList == null) {
            mListenerList = new ArrayList<>();
        }
        mListenerList.add(l);
    }
    //为了控制显示界面的listview在键盘弹出的时候滑动到底部
    public interface OnFuncKeyBoardListener {
        /**
         * 功能布局弹起
         */
        void onFuncPop(int height);

        /**
         * 功能布局关闭
         */
        void onFuncClose();
    }

    private OnFuncChangeListener onFuncChangeListener;

    public interface OnFuncChangeListener {
        void onFuncChange(int key);
    }

    public void setOnFuncChangeListener(OnFuncChangeListener listener) {
        this.onFuncChangeListener = listener;
    }
}

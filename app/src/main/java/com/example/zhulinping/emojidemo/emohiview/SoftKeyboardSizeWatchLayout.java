package com.example.zhulinping.emojidemo.emohiview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class SoftKeyboardSizeWatchLayout extends RelativeLayout {
    private Context mContext;
    //屏幕可见区域高度old and new
    private int mOldh = -1;
    private int mNowh = -1;
    protected int mScreenHeight = 0;
    protected boolean mIsSoftKeyboardPop = false;

    public SoftKeyboardSizeWatchLayout(Context context, AttributeSet arrs) {
        super(context,arrs);
        mContext = context;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                if (mScreenHeight == 0) {
                    mScreenHeight = r.bottom;
                }
                mNowh = mScreenHeight - r.bottom;
                if (mOldh != -1 && mOldh != mNowh) {
                    if (mNowh > 0) {
                        mIsSoftKeyboardPop = true;
                        if (mListenerList != null && mListenerList.size() > 0) {
                            for (OnResizeListener listener : mListenerList) {
                                listener.onSoftPop(mNowh);
                            }
                        }
                    } else {
                        mIsSoftKeyboardPop = false;
                        if (mListenerList != null && mListenerList.size() > 0) {
                            for (OnResizeListener listener : mListenerList) {
                                listener.onSoftClose();
                            }
                        }
                    }
                }
                mOldh = mNowh;
            }
        });
    }

    public boolean isSoftKeyboardPop() {
        return mIsSoftKeyboardPop;
    }

    private List<OnResizeListener> mListenerList;

    public void addOnResizeListener(OnResizeListener l) {
        if (mListenerList == null) {
            mListenerList = new ArrayList<>();
        }
        mListenerList.add(l);
    }

    //实现在 AutoHeightLayout
    public interface OnResizeListener {
        /**
         * 软键盘弹起
         */
        void onSoftPop(int height);

        /**
         * 软键盘关闭
         */
        void onSoftClose();
    }
}

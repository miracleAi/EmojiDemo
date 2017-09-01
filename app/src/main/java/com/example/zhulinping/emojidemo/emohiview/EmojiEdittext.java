package com.example.zhulinping.emojidemo.emohiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.zhulinping.emojidemo.display.filter.BaseEmojiFilter;

import java.util.ArrayList;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiEdittext extends EditText {

    private ArrayList<BaseEmojiFilter> mFilterList;

    public EmojiEdittext(Context context) {
        this(context, null);
    }

    public EmojiEdittext(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh > 0 && onSizeChangedListener != null) {
            onSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    public void setGravity(int gravity) {
        try {
            super.setGravity(gravity);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.setGravity(gravity);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        try {
            super.setText(text, type);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(text.toString());
        }
    }

    @Override
    protected final void onTextChanged(CharSequence arg0, int start, int lengthBefore, int after) {
        super.onTextChanged(arg0, start, lengthBefore, after);
        if (mFilterList == null) {
            return;
        }
        for (BaseEmojiFilter emoticonFilter : mFilterList) {
            emoticonFilter.filter(this, arg0, start, lengthBefore, after);
        }
    }

    public void addEmoticonFilter(BaseEmojiFilter emoticonFilter) {
        if (mFilterList == null) {
            mFilterList = new ArrayList<>();
        }
        mFilterList.add(emoticonFilter);
    }

    public void removedEmoticonFilter(BaseEmojiFilter emoticonFilter) {
        if (mFilterList != null && mFilterList.contains(emoticonFilter)) {
            mFilterList.remove(emoticonFilter);
        }
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (onBackKeyClickListener != null) {
            onBackKeyClickListener.onBackKeyClick();
        }
        return super.dispatchKeyEventPreIme(event);
    }

    public interface OnBackKeyClickListener {
        void onBackKeyClick();
    }

    OnBackKeyClickListener onBackKeyClickListener;

    public void setOnBackKeyClickListener(OnBackKeyClickListener i) {
        onBackKeyClickListener = i;
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }

    OnSizeChangedListener onSizeChangedListener;

    public void setOnSizeChangedListener(OnSizeChangedListener i) {
        onSizeChangedListener = i;
    }
}

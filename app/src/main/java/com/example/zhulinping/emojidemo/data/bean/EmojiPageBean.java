package com.example.zhulinping.emojidemo.data.bean;

import android.view.View;
import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.emohiview.EmojiPageItemView;

import java.util.List;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiPageBean<T> extends PageBean<EmojiPageBean>{
    public enum DelBtnStatus {
        // 0,1,2
        GONE, FOLLOW, LAST;

        public boolean isShow() {
            return ! GONE.toString().equals(this.toString());
        }
    }
    /**
     * 表情数据源
     */
    private List<T> mEmoticonList;
    /**
     * 每页行数
     */
    private int mLine;
    /**
     * 每页列数
     */
    private int mRow;
    /**
     * 删除按钮
     */
    private DelBtnStatus mDelBtnStatus;

    public List<T> getEmoticonList() {
        return mEmoticonList;
    }

    public void setEmoticonList(List<T> emoticonList) {
        this.mEmoticonList = emoticonList;
    }

    public int getLine() {
        return mLine;
    }

    public void setLine(int line) {
        this.mLine = line;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public DelBtnStatus getDelBtnStatus() {
        return mDelBtnStatus;
    }

    public void setDelBtnStatus(DelBtnStatus delBtnStatus) {
        this.mDelBtnStatus = delBtnStatus;
    }

    public EmojiPageBean() { }

    public View instantiateItem(final ViewGroup container, int position, EmojiPageBean pageEntity) {
        if(mPageViewInstantiateListener != null){
            return mPageViewInstantiateListener.instantiateItem(container, position, this);
        }
        if (getRootView() == null) {
            EmojiPageItemView pageView = new EmojiPageItemView(container.getContext());
            pageView.setNumColumns(mRow);
            setRootView(pageView);
        }
        return getRootView();
    }
}

package com.example.zhulinping.emojidemo.data.bean;

import android.util.Log;

import com.example.zhulinping.emojidemo.interfaces.PageViewInstantiateListener;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiPageSetBean<T> extends PageSetBean<EmojiPageBean> {
    private int mLine;
    private int mRow;
    EmojiPageBean.DelBtnStatus mDelBtnStatus;
    ArrayList<T> mEmoticonList;

    private EmojiPageSetBean(Builder builder) {
        super(builder);
        mLine = builder.mLine;
        mRow = builder.mRow;
        mDelBtnStatus = builder.mDelBtnStatus;
        mEmoticonList = builder.mEmoticonList;
    }

    public int getmRow() {
        return mRow;
    }

    public EmojiPageBean.DelBtnStatus getmDelBtnStatus() {
        return mDelBtnStatus;
    }

    public ArrayList<T> getmEmoticonList() {
        return mEmoticonList;
    }

    public int getmLine() {

        return mLine;
    }

    public static class Builder<T> extends PageSetBean.Builder {
        private int mLine;
        private int mRow;
        private EmojiPageBean.DelBtnStatus mDelBtnStatus = EmojiPageBean.DelBtnStatus.GONE;
        private String emojiSetId;
        private ArrayList<T> mEmoticonList;
        protected PageViewInstantiateListener pageViewInstantiateListener;

        public Builder() {
        }

        public Builder mLine(int val) {
            mLine = val;
            return this;
        }

        public Builder mRow(int val) {
            mRow = val;
            return this;
        }

        public Builder mDelBtnStatus(EmojiPageBean.DelBtnStatus val) {
            mDelBtnStatus = val;
            return this;
        }

        public Builder emojiSetId(String val) {
            emojiSetId = val;
            return this;
        }

        public Builder mEmoticonList(ArrayList<T> val) {
            mEmoticonList = val;
            return this;
        }

        public Builder setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) {
            this.pageViewInstantiateListener = pageViewInstantiateListener;
            return this;
        }
        public Builder emojiSetIcon(String val) {
            emojiSetIcon = val;
            return this;
        }

        public Builder emojiSetName(String val) {
            emojiSetName = val;
            return this;
        }

        public Builder emojiSetPageCount(int val) {
            emojiSetPageCount = val;
            return this;
        }

        public Builder isShowIndicator(boolean val) {
            isShowIndicator = val;
            return this;
        }
        public EmojiPageSetBean build() {
            int emoticonSetSum = mEmoticonList.size();
            int del = mDelBtnStatus.isShow() ? 1 : 0;
            int everyPageMaxSum = mRow * mLine - del;
            emojiSetPageCount = (int) Math.ceil((double) mEmoticonList.size() / everyPageMaxSum);

            int start = 0;
            int end = everyPageMaxSum > emoticonSetSum ? emoticonSetSum : everyPageMaxSum;

            if (!emojiPageList.isEmpty()) {
                emojiPageList.clear();
            }

            for (int i = 0; i < emojiSetPageCount; i++) {
                EmojiPageBean emoticonPageEntity = new EmojiPageBean();
                emoticonPageEntity.setLine(mLine);
                emoticonPageEntity.setRow(mRow);
                emoticonPageEntity.setDelBtnStatus(mDelBtnStatus);
                emoticonPageEntity.setEmoticonList(mEmoticonList.subList(start, end));
                emoticonPageEntity.setIPageViewInstantiateItem(pageViewInstantiateListener);
                emojiPageList.add(emoticonPageEntity);

                start = everyPageMaxSum + i * everyPageMaxSum;
                end = everyPageMaxSum + (i + 1) * everyPageMaxSum;
                if (end >= emoticonSetSum) {
                    end = emoticonSetSum;
                }
            }
            return new EmojiPageSetBean(this);
        }
    }
}

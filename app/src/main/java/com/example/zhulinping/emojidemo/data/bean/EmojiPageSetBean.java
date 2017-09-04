package com.example.zhulinping.emojidemo.data.bean;

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
        private EmojiPageBean.DelBtnStatus mDelBtnStatus;
        private String emojiSetId;
        private ArrayList<T> mEmoticonList;

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

        public EmojiPageSetBean build() {
            return new EmojiPageSetBean(this);
        }
    }
}

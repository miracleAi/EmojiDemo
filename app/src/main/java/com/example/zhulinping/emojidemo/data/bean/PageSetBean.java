package com.example.zhulinping.emojidemo.data.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class PageSetBean<T extends PageBean> implements Serializable {
    protected String emojiSetId = UUID.randomUUID().toString();
    protected String emojiSetIcon;
    protected String emojiSetName;
    protected int emojiSetPageCount;
    protected LinkedList<T> emojiPageList;
    protected boolean isShowIndicator;

    public PageSetBean(Builder builder) {
        this.emojiSetIcon = builder.emojiSetIcon;
        this.emojiSetName = builder.emojiSetName;
        this.emojiSetPageCount = builder.emojiSetPageCount;
        this.emojiPageList = builder.emojiPageList;
        this.isShowIndicator = builder.isShowIndicator;
    }

    public boolean isShowIndicator() {
        return isShowIndicator;
    }


    public String getEmojiSetId() {
        return emojiSetId;
    }


    public String getEmojiSetIcon() {
        return emojiSetIcon;
    }


    public String getEmojiSetName() {
        return emojiSetName;
    }


    public int getEmojiSetPageCount() {
        return emojiPageList == null ? 0 : emojiPageList.size();
    }


    public LinkedList<T> getEmojiPageList() {
        return emojiPageList;
    }


    public static class Builder<T extends PageBean> {
        protected String emojiSetIcon;
        protected String emojiSetName;
        protected int emojiSetPageCount;
        protected LinkedList<T> emojiPageList = new LinkedList<>();
        protected boolean isShowIndicator;

        public Builder() {
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

        public Builder emojiPageList(LinkedList<T> val) {
            emojiPageList = val;
            return this;
        }

        public Builder isShowIndicator(boolean val) {
            isShowIndicator = val;
            return this;
        }


        public PageSetBean build() {
            return new PageSetBean(this);
        }
    }
}

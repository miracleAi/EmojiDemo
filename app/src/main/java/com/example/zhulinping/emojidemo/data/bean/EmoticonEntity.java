package com.example.zhulinping.emojidemo.data.bean;

/**
 * Created by zhulinping on 2017/9/7.
 */

public class EmoticonEntity {

    private String mIconUri;
    private String mContent;

    public String getIconUri() {
        return mIconUri;
    }

    public void setIconUri(String iconUri) {
        this.mIconUri = iconUri;
    }

    public void setIconUri(int iconUri) {
        this.mIconUri = "" + iconUri;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public EmoticonEntity(String iconUri, String content) {
        this.mIconUri = iconUri;
        this.mContent = content;
    }

    public EmoticonEntity(String content) {
        this.mContent = content;
    }
}


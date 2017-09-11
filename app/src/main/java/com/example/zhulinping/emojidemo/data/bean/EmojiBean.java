package com.example.zhulinping.emojidemo.data.bean;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiBean {
    public int icon;
    public String emoji;
    public String arraySpec;

    public EmojiBean(int icon, String emoji) {
        this.icon = icon;
        this.emoji = emoji;
    }
    public EmojiBean(){}

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getArraySpec() {
        return arraySpec;
    }

    public void setArraySpec(String arraySpec) {
        this.arraySpec = arraySpec;
    }
}

package com.example.zhulinping.emojidemo.display.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.zhulinping.emojidemo.data.CustemEmojis;
import com.example.zhulinping.emojidemo.display.span.EmojiSpan;
import com.example.zhulinping.emojidemo.utils.EmojiKeyboardUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class CustomEmojiFilter extends BaseEmojiFilter {
    public static final Pattern BIG_RANGE = Pattern.compile("\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]");
    private int emoticonSize = -1;

    @Override
    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        emoticonSize = emoticonSize == -1 ? EmojiKeyboardUtils.getFontHeight(editText) : emoticonSize;
        //clearSpan(editText.getText(), start, text.toString().length());
        Matcher m = BIG_RANGE.matcher(text.toString().substring(start, text.toString().length()));
        if (m != null) {
            while (m.find()) {
                String key = m.group();
                String icon = CustemEmojis.sXhsEmoticonHashMap.get(key);
                if (!TextUtils.isEmpty(icon)) {
                    emoticonDisplay(editText.getContext(), editText.getText(), icon, emoticonSize, start + m.start(), start + m.end());
                }
            }
        }
    }
    public static void emoticonDisplay(Context context, Spannable spannable, String emoticonName, int fontSize, int start, int end) {
        Drawable drawable = getDrawableFromAssets(context, emoticonName);
        if (drawable != null) {
            int itemHeight;
            int itemWidth;
            if (fontSize == -1) {
                itemHeight = drawable.getIntrinsicHeight();
                itemWidth = drawable.getIntrinsicWidth();
            } else {
                itemHeight = fontSize;
                itemWidth = fontSize;
            }

            drawable.setBounds(0, 0, itemHeight, itemWidth);
            EmojiSpan imageSpan = new EmojiSpan(drawable);
            spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }
}
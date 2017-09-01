package com.example.zhulinping.emojidemo.display.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.widget.EditText;

import com.example.zhulinping.emojidemo.display.span.EmojiSpan;
import com.example.zhulinping.emojidemo.utils.EmojiKeyboardUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmotionFilter extends BaseEmojiFilter {
    private static final Pattern EMOJI_RANGE = Pattern.compile("[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]");
    private int emojiSize = -1;

    @Override
    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        emojiSize = emojiSize == -1 ? EmojiKeyboardUtils.getFontHeight(editText) : emojiSize;
        clearSpan(editText.getText(), start, text.toString().length());
        Matcher macher = EMOJI_RANGE.matcher(text.toString().substring(start, text.toString().length()));
        if (macher != null) {
            while (macher.find()) {
                //转换为16进制编码
                String emojiHex = Integer.toHexString(Character.codePointAt(macher.group(), 0));
                emojiDisplay(editText.getContext(), editText.getText(), emojiHex, emojiSize, start + macher.start(), start + macher.end());
            }
        }

    }

    public static void emojiDisplay(Context context, Spannable spannable, String emojiHex, int fontSize, int start, int end) {
        Drawable drawable = getDrawable(context, "emoji_0x" + emojiHex);
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
            spannable.setSpan(imageSpan, start, end, 17);
        }

    }

}

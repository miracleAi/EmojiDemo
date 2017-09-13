package com.example.zhulinping.emojidemo.display.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.widget.EditText;

import com.example.zhulinping.emojidemo.display.span.EmojiSpan;
import com.example.zhulinping.emojidemo.utils.CodesArrayParser;
import com.example.zhulinping.emojidemo.utils.EmojiKeyboardUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhulinping on 2017/9/12.
 */

public class ZlpEmoticonFilter extends BaseEmojiFilter{
    private static final Pattern EMOJI_RANGE = Pattern.compile("(?:[\uD83C\uDF00-\uD83D\uDDFF]" +
            "|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]" +
            "|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}" +
            "|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?" +
            "|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?" +
            "|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?" +
            "|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?" +
            "|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?" +
            "|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?" +
            "|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?" +
            "|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)");
    private int emojiSize = -1;

    @Override
    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        emojiSize = emojiSize == -1 ? EmojiKeyboardUtils.getFontHeight(editText) : emojiSize;
        //clearSpan(editText.getText(), start, text.toString().length());
        String emojiStr = text.toString().substring(start, text.toString().length());
        String filterCode = CodesArrayParser.parseLabel(emojiStr);
        //不是表情 直接返回
        if(filterCode == null){
            return;
        }
        Matcher macher = EMOJI_RANGE.matcher(filterCode);
        if (macher != null) {
            while (macher.find()) {
                String resId = CodesArrayParser.getLabelRes(emojiStr);
                emojiDisplay(editText.getContext(), editText.getText(), resId, emojiSize, start, start + emojiStr.length());
            }
        }

    }
    public static void emojiDisplay(Context context, Spannable spannable, String emojiHex, int fontSize, int start, int end) {
        Drawable drawable = getDrawable(context, "emoji" + emojiHex);
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

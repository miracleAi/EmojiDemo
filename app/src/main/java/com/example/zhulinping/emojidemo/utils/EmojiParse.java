package com.example.zhulinping.emojidemo.utils;

import android.text.TextUtils;

import com.example.zhulinping.emojidemo.data.bean.EmoticonEntity;
import com.example.zhulinping.emojidemo.utils.imageloader.ImageBase;

import java.util.ArrayList;

/**
 * Created by zhulinping on 2017/9/6.
 */

public class EmojiParse {
    public EmojiParse() {
    }

    public static String fromChar(char ch) {
        return Character.toString(ch);
    }

    public static String fromCodePoint(int codePoint) {
        return newString(codePoint);
    }

    public static final String newString(int codePoint) {
        return Character.charCount(codePoint) == 1?String.valueOf(codePoint):new String(Character.toChars(codePoint));
    }
    public static ArrayList<EmoticonEntity> ParseXhsData(String[] arry, ImageBase.Scheme scheme) {
        try {
            ArrayList<EmoticonEntity> emojis = new ArrayList<>();
            for (int i = 0; i < arry.length; i++) {
                if (!TextUtils.isEmpty(arry[i])) {
                    String temp = arry[i].trim().toString();
                    String[] text = temp.split(",");
                    if (text != null && text.length == 2) {
                        String fileName;
                        if (scheme == ImageBase.Scheme.DRAWABLE) {
                            if (text[0].contains(".")) {
                                fileName = scheme.toUri(text[0].substring(0, text[0].lastIndexOf(".")));
                            } else {
                                fileName = scheme.toUri(text[0]);
                            }
                        } else {
                            fileName = scheme.toUri(text[0]);
                        }
                        String content = text[1];
                        EmoticonEntity bean = new EmoticonEntity(fileName, content);
                        emojis.add(bean);
                    }
                }
            }
            return emojis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

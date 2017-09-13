package com.example.zhulinping.emojidemo.utils;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.zhulinping.emojidemo.R;
import com.example.zhulinping.emojidemo.adapter.PageSetAdapter;
import com.example.zhulinping.emojidemo.data.EmojiModel;
import com.example.zhulinping.emojidemo.data.bean.EmojiBean;
import com.example.zhulinping.emojidemo.data.bean.EmojiPageBean;
import com.example.zhulinping.emojidemo.data.bean.EmojiPageSetBean;
import com.example.zhulinping.emojidemo.data.bean.EmoticonEntity;
import com.example.zhulinping.emojidemo.interfaces.EmoticonClickListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhulinping on 2017/9/11.
 */

public class ApusXmlParse {
    public static List<EmojiPageSetBean> getXmlRecourse(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("emoji-categories.xml");
            return parseXml(context, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<EmojiPageSetBean> parseXml(Context context, InputStream in) {
        List<EmojiPageSetBean> setList = new ArrayList<>();
        EmojiPageSetBean.Builder pageSetBean = null;
        ArrayList<EmojiBean> emojiList = null;
        ArrayList<EmoticonEntity> emotionList = null;
        String setName = null;
        if (in == null) {
            return null;
        }
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name == null) {
                            break;
                        }
                        if (name.equals("array")) {
                            pageSetBean = new EmojiPageSetBean.Builder();
                            setName = parser.getAttributeValue(null, "name");
                            pageSetBean.emojiSetId(setName);
                            if (setName != null && setName.equals("emoji_emoticons") || setName.startsWith("emoticon")) {
                                emotionList = new ArrayList<>();
                            } else {
                                emojiList = new ArrayList<>();
                            }

                        } else if (name.equals("item")) {
                            String arraySpec = parser.nextText();
                            if (setName != null && setName.equals("emoji_emoticons") || setName.startsWith("emoticon")) {
                                EmoticonEntity entity = new EmoticonEntity(arraySpec);
                                emotionList.add(entity);
                            } else {
                                EmojiBean emojiBean = new EmojiBean();
                                emojiBean.setArraySpec(arraySpec);
                                String imageName = "emoji" + CodesArrayParser.getLabelRes(arraySpec);
                                int resID = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
                                if (resID <= 0) {
                                    resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                                }
                                emojiBean.setIcon(resID);
                                emojiBean.setEmoji(CodesArrayParser.parseLabel(arraySpec));
                                emojiList.add(emojiBean);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endName = parser.getName();
                        if (endName == null) {
                            break;
                        }
                        if (endName.equals("array")) {
                            if (setName != null && setName.equals("emoji_emoticons") || setName.startsWith("emoticon")) {
                                pageSetBean.mEmoticonList(emotionList);
                            } else {
                                pageSetBean.mEmoticonList(emojiList);
                            }
                            setList.add(new EmojiPageSetBean(pageSetBean));
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return setList;
    }
}

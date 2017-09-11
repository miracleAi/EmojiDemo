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
    public static List<EmojiPageSetBean<EmojiBean>> getXmlRecourse(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("emoji-categories.xml");
            return parseXml(context,inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<EmojiPageSetBean<EmojiBean>> parseXml(Context context, InputStream in) {
        List<EmojiPageSetBean<EmojiBean>> setList = new ArrayList<>();
        EmojiPageSetBean.Builder pageSetBean = null;
        ArrayList<EmojiBean> emojiList = null;
        EmojiBean emojiBean = null;
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
                           emojiList = new ArrayList<>();
                            pageSetBean = new EmojiPageSetBean.Builder();
                            //pageSetBean.emojiSetId(parser.getAttributeValue(null, "name"));
                        } else if (name.equals("item")) {
                            emojiBean = new EmojiBean();
                            String arraySpec = parser.nextText();
                            emojiBean.setArraySpec(arraySpec);
                            String imageName = "emoji"+CodesArrayParser.getLabelRes(arraySpec);
                            int resID = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
                            if (resID <= 0) {
                                resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                            }
                            emojiBean.setIcon(resID);
                            emojiBean.setEmoji(CodesArrayParser.parseLabel(arraySpec));
                            emojiList.add(emojiBean);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endName = parser.getName();
                        if (endName == null) {
                            break;
                        }
                        if (endName.equals("array")) {
                            pageSetBean.mEmoticonList(emojiList);
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

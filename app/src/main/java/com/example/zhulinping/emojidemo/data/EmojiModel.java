package com.example.zhulinping.emojidemo.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.R;
import com.example.zhulinping.emojidemo.adapter.EmoticonsAdapter;
import com.example.zhulinping.emojidemo.adapter.PageSetAdapter;
import com.example.zhulinping.emojidemo.data.bean.EmojiBean;
import com.example.zhulinping.emojidemo.data.bean.EmojiPageBean;
import com.example.zhulinping.emojidemo.data.bean.EmojiPageSetBean;
import com.example.zhulinping.emojidemo.data.bean.EmoticonEntity;
import com.example.zhulinping.emojidemo.display.filter.CustomEmojiFilter;
import com.example.zhulinping.emojidemo.display.filter.EmotionFilter;
import com.example.zhulinping.emojidemo.emohiview.EmojiEdittext;
import com.example.zhulinping.emojidemo.emohiview.EmojiPageItemView;
import com.example.zhulinping.emojidemo.interfaces.EmoticonClickListener;
import com.example.zhulinping.emojidemo.interfaces.EmoticonDisplayListener;
import com.example.zhulinping.emojidemo.interfaces.PageViewInstantiateListener;
import com.example.zhulinping.emojidemo.utils.Constants;
import com.example.zhulinping.emojidemo.utils.EmojiParse;
import com.example.zhulinping.emojidemo.utils.imageloader.ImageBase;
import com.example.zhulinping.emojidemo.utils.imageloader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiModel {
    public static void  init(EmojiEdittext emojiEdt) {
        emojiEdt.addEmoticonFilter(new CustomEmojiFilter());
        emojiEdt.addEmoticonFilter(new EmotionFilter());
    }
    public static PageSetAdapter mPageSetAdpter;
    public static PageSetAdapter initPageSetAdapter(EmoticonClickListener listener){
        if(mPageSetAdpter != null){
            return mPageSetAdpter;
        }
        mPageSetAdpter = new PageSetAdapter();
        addEmotionSet(mPageSetAdpter,listener);
        addXhsPageSetEntity(mPageSetAdpter,listener);
        return mPageSetAdpter;
    }
    //加入emoji表情
    public static void addEmotionSet(PageSetAdapter adapter, final EmoticonClickListener listener){
        ArrayList<EmojiBean> emojiArray = new ArrayList<>();
        Collections.addAll(emojiArray, DefEmoticons.sEmojiArray);
        EmojiPageSetBean pageSetBean = new EmojiPageSetBean.Builder()
                .mLine(3)
                .mRow(7)
                .mEmoticonList(emojiArray)
                .setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(new EmoticonDisplayListener<Object>() {
                    @Override
                    public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {
                        final EmojiBean emojiBean = (EmojiBean) object;
                        if (emojiBean == null && !isDelBtn) {
                            return;
                        }

                        viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);

                        if (isDelBtn) {
                            viewHolder.iv_emoticon.setImageResource(R.mipmap.icon_del);
                        } else {
                            viewHolder.iv_emoticon.setImageResource(emojiBean.icon);
                        }

                        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onEmoticonClick(emojiBean, Constants.EMOTICON_CLICK_TEXT, isDelBtn);
                                }
                            }
                        });
                    }
                }))
                .mDelBtnStatus(EmojiPageBean.DelBtnStatus.LAST)
                .emojiSetIcon(ImageBase.Scheme.DRAWABLE.toUri("icon_emoji"))
                .build();
        adapter.add(pageSetBean);

    }
    /**
     * 插入xhs表情集
     *
     * @param pageSetAdapter
     * @param emoticonClickListener
     */
    public static void addXhsPageSetEntity(PageSetAdapter pageSetAdapter, EmoticonClickListener emoticonClickListener) {
        EmojiPageSetBean xhsPageSetEntity
                = new EmojiPageSetBean.Builder()
                .mLine(3)
                .mRow(7)
                .mEmoticonList(EmojiParse.ParseXhsData(CustemEmojis.xhsEmoticonArray, ImageBase.Scheme.ASSETS))
                .setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(getCommonEmoticonDisplayListener(emoticonClickListener, Constants.EMOTICON_CLICK_TEXT)))
                .mDelBtnStatus(EmojiPageBean.DelBtnStatus.LAST)
                .emojiSetIcon(ImageBase.Scheme.ASSETS.toUri("xhsemoji_19.png"))
                .build();
        pageSetAdapter.add(xhsPageSetEntity);
    }

    public static PageViewInstantiateListener<EmojiPageBean> getDefaultEmoticonPageViewInstantiateItem(final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return getEmoticonPageViewInstantiateItem(EmoticonsAdapter.class, null, emoticonDisplayListener);
    }
    public static PageViewInstantiateListener<EmojiPageBean> getEmoticonPageViewInstantiateItem(final Class _class, final EmoticonClickListener onEmoticonClickListener, final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return new PageViewInstantiateListener<EmojiPageBean>() {
            @Override
            public View instantiateItem(ViewGroup container, int position, EmojiPageBean pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmojiPageItemView pageView = new EmojiPageItemView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        EmoticonsAdapter adapter = (EmoticonsAdapter) newInstance(_class, container.getContext(), pageEntity, onEmoticonClickListener);
                        if (emoticonDisplayListener != null) {
                            adapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        };
    }
    public static EmoticonDisplayListener<Object> getCommonEmoticonDisplayListener(final EmoticonClickListener onEmoticonClickListener, final int type) {
        return new EmoticonDisplayListener<Object>() {
            @Override
            public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {

                final EmoticonEntity emoticonEntity = (EmoticonEntity) object;
                if (emoticonEntity == null && !isDelBtn) {
                    return;
                }
                viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);

                if (isDelBtn) {
                    viewHolder.iv_emoticon.setImageResource(R.mipmap.icon_del);
                } else {
                    try {
                        ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEmoticonClickListener != null) {
                            onEmoticonClickListener.onEmoticonClick(emoticonEntity, type, isDelBtn);
                        }
                    }
                });
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static Object newInstance(Class _Class, Object... args) throws Exception {
        return newInstance(_Class, 0, args);
    }
}

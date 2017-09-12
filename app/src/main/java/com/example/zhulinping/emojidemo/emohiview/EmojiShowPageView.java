package com.example.zhulinping.emojidemo.emohiview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.example.zhulinping.emojidemo.adapter.PageSetAdapter;
import com.example.zhulinping.emojidemo.data.bean.PageSetBean;

/**
 * Created by zhulinping on 2017/9/4.
 */

public class EmojiShowPageView extends ViewPager {
    protected PageSetAdapter mPageSetAdapter;
    protected int mCurrentPagePosition;

    public EmojiShowPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(PageSetAdapter adapter) {
        super.setAdapter(adapter);
        this.mPageSetAdapter = adapter;
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkPageChange(position);
                mCurrentPagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (mOnEmoticonsPageViewListener == null
                || mPageSetAdapter.getPageSetEntityList().isEmpty()) {
            return;
        }
        PageSetBean pageSetEntity = mPageSetAdapter.getPageSetEntityList().get(0);
        mOnEmoticonsPageViewListener.playTo(0, pageSetEntity);
        mOnEmoticonsPageViewListener.emoticonSetChanged(pageSetEntity);
    }
    //切换表情集toolbar时调用
    public void setCurrentPageSet(PageSetBean pageSetEntity) {
        if (mPageSetAdapter == null || mPageSetAdapter.getCount() <= 0) {
            return;
        }
        //表情集初始页index
        setCurrentItem(mPageSetAdapter.getPageSetStartPosition(pageSetEntity));
    }
    private void checkPageChange(int position) {
        if (mPageSetAdapter == null) {
            return;
        }
        int lastEnd = 0;
        for (PageSetBean bean : mPageSetAdapter.getPageSetEntityList()) {
            int size = bean.getEmojiSetPageCount();
            //position指向页是当前访问表情集中页面，否则lastEnd指向上一数据集末尾，size为新表情集长度
            if (lastEnd + size > position) {
                //标记表情集是否变化
                boolean isEmoticonSetChanged = true;
                if (mCurrentPagePosition - lastEnd >= size) {
                    //上一表情集
                    if (mOnEmoticonsPageViewListener != null) {
                        mOnEmoticonsPageViewListener.playTo(position - lastEnd, bean);
                    }
                } else if (mCurrentPagePosition - lastEnd < 0) {
                    //下一表情集
                    if(mOnEmoticonsPageViewListener != null){
                        mOnEmoticonsPageViewListener.playTo(0,bean);
                    }
                }else{
                    //当前表情集
                    if(mOnEmoticonsPageViewListener != null){
                        mOnEmoticonsPageViewListener.playBy(mCurrentPagePosition - lastEnd,position - lastEnd,bean);
                    }
                    isEmoticonSetChanged = false;
                }
                if (isEmoticonSetChanged && mOnEmoticonsPageViewListener != null) {
                    mOnEmoticonsPageViewListener.emoticonSetChanged(bean);
                }
                return;
            }
            lastEnd = lastEnd + size;
        }

    }

    private OnEmoticonsPageViewListener mOnEmoticonsPageViewListener;

    public void setOnIndicatorListener(OnEmoticonsPageViewListener listener) {
        mOnEmoticonsPageViewListener = listener;
    }

    public interface OnEmoticonsPageViewListener {

        void emoticonSetChanged(PageSetBean pageSetEntity);

        /**
         * @param position 相对于当前表情集的位置
         */
        void playTo(int position, PageSetBean pageSetEntity);

        /**
         * @param oldPosition 相对于当前表情集的始点位置
         * @param newPosition 相对于当前表情集的终点位置
         */
        void playBy(int oldPosition, int newPosition, PageSetBean pageSetEntity);
    }
}

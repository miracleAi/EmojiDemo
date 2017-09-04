package com.example.zhulinping.emojidemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhulinping.emojidemo.data.bean.PageBean;
import com.example.zhulinping.emojidemo.data.bean.PageSetBean;

import java.util.ArrayList;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class PageSetAdapter extends PagerAdapter {
    private final ArrayList<PageSetBean> mPageSetEntityList = new ArrayList<>();

    public ArrayList<PageSetBean> getPageSetEntityList() {
        return mPageSetEntityList;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (PageSetBean pageSetEntity : mPageSetEntityList) {
            count += pageSetEntity.getEmojiSetPageCount();
        }
        return count;
    }
    //获得指定为表情页
    public PageBean getPageEntity(int position) {
        for (PageSetBean pageSetEntity : mPageSetEntityList) {
            if (pageSetEntity.getEmojiSetPageCount() > position) {
                return (PageBean) pageSetEntity.getEmojiPageList().get(position);
            } else {
                position -= pageSetEntity.getEmojiSetPageCount();
            }
        }
        return null;
    }
    //获得表情集第一页所在position
    public int getPageSetStartPosition(PageSetBean pageSetEntity) {
        if (pageSetEntity == null || TextUtils.isEmpty(pageSetEntity.getEmojiSetId())) {
            return 0;
        }

        int startPosition = 0;
        for (int i = 0; i < mPageSetEntityList.size(); i++) {
            if (i == mPageSetEntityList.size() - 1 && !pageSetEntity.getEmojiSetId().equals(mPageSetEntityList.get(i).getEmojiSetId())) {
                return 0;
            }
            if (pageSetEntity.getEmojiSetId().equals(mPageSetEntityList.get(i).getEmojiSetId())) {
                return startPosition;
            }
            startPosition += mPageSetEntityList.get(i).getEmojiSetPageCount();
        }
        return startPosition;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //可以加入不同的itemView
        View view = getPageEntity(position).instantiateItem(container, position, null);
        if (view == null) {
            return null;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
